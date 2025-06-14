package com.tecnocampus.kingdoms.application;

import com.tecnocampus.kingdoms.application.dto.KingdomDTO;
import com.tecnocampus.kingdoms.domain.Kingdom;
import com.tecnocampus.kingdoms.persistence.KingdomRepository;
import org.springframework.stereotype.Service;

@Service
public class KingdomService {
    private final KingdomRepository repository;

    public KingdomService(KingdomRepository repository) {
        this.repository = repository;
    }

    public KingdomDTO createKingdom(KingdomDTO dto) {
        Kingdom kingdom = new Kingdom(dto.gold(), dto.citizens(), dto.food());
        repository.save(kingdom);
        return kingdom.toDTO();
    }

    public KingdomDTO startProduction(String id) {
        Kingdom kingdom = repository.findById(id);
        kingdom.startProduction();
        if (kingdom.getCitizens() <= 0) {
            repository.delete(id);
            return null;
        }
        repository.update(kingdom);
        return kingdom.toDTO();
    }

    public KingdomDTO invest(String id, String type, int goldAmount) {
        Kingdom kingdom = repository.findById(id);
        switch (type) {
            case "food" -> kingdom.investFood(goldAmount);
            case "citizens" -> kingdom.investCitizens(goldAmount);
            default -> throw new IllegalArgumentException("Invalid type");
        }
        repository.update(kingdom);
        return kingdom.toDTO();
    }

    public KingdomDTO getStatus(String id) {
        return repository.findById(id).toDTO();
    }

    public KingdomDTO getRichest() {
        return repository.getRichest().toDTO();
    }

    public KingdomDTO attack(String id, String targetId) {
        Kingdom attacker = repository.findById(id);
        Kingdom defender = repository.findById(targetId);
        Kingdom winner;
        Kingdom loser;
        if (attacker.getCitizens() > defender.getCitizens()) {
            winner = attacker;
            loser = defender;
        } else if (attacker.getCitizens() < defender.getCitizens()) {
            winner = defender;
            loser = attacker;
        } else {
            winner = defender;
            loser = attacker; // tie -> defender wins
        }

        winner.addGold(loser.getGold());
        loser.removeGold(loser.getGold());

        int stolen = loser.getCitizens() / 2;
        winner.addCitizens(stolen);
        loser.removeCitizens(stolen);

        repository.update(winner);
        if (loser.getCitizens() <= 0) {
            repository.delete(loser.getId());
        } else {
            repository.update(loser);
        }

        return winner.toDTO();
    }
}

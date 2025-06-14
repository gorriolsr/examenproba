package com.tecnocampus.kingdoms.persistence;

import com.tecnocampus.kingdoms.domain.Kingdom;
import com.tecnocampus.outlaws.utilities.NotFoundException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Repository
@Profile("in-memory")
public class InMemoryKingdomRepository implements KingdomRepository {
    private final List<Kingdom> kingdoms = new ArrayList<>();

    @Override
    public Kingdom save(Kingdom kingdom) {
        kingdoms.add(kingdom);
        return kingdom;
    }

    @Override
    public Kingdom findById(String id) {
        return kingdoms.stream()
                .filter(k -> k.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Kingdom not found"));
    }

    @Override
    public void delete(String id) {
        kingdoms.removeIf(k -> k.getId().equals(id));
    }

    @Override
    public List<Kingdom> findAll() {
        return new ArrayList<>(kingdoms);
    }

    @Override
    public Kingdom getRichest() {
        return kingdoms.stream()
                .max(Comparator.comparingInt(Kingdom::getGold))
                .orElseThrow(() -> new NotFoundException("No kingdoms"));
    }

    @Override
    public void update(Kingdom kingdom) {
        // nothing needed for in-memory since object is updated directly
    }
}

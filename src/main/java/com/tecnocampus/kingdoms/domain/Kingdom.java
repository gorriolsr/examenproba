package com.tecnocampus.kingdoms.domain;

import com.tecnocampus.kingdoms.application.dto.KingdomDTO;
import com.tecnocampus.outlaws.utilities.InvalidDataException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class Kingdom {
    private String id = UUID.randomUUID().toString();
    private int gold;
    private int citizens;
    private int food;
    private LocalDateTime createdAt = LocalDateTime.now();

    public Kingdom() {}

    public Kingdom(int gold, int citizens, int food) {
        validateRange(gold);
        validateRange(citizens);
        validateRange(food);
        this.gold = gold;
        this.citizens = citizens;
        this.food = food;
    }

    public Kingdom(ResultSet rs) throws SQLException {
        this.id = rs.getString("id");
        this.gold = rs.getInt("gold");
        this.citizens = rs.getInt("citizens");
        this.food = rs.getInt("food");
        this.createdAt = Optional.of(rs.getTimestamp("created_at"))
                .map(Timestamp::toLocalDateTime)
                .get();
    }

    private void validateRange(int value) {
        if (value < 0 || value > 60) {
            throw new InvalidDataException("Values must be between 0 and 60");
        }
    }

    public String getId() {
        return id;
    }

    public int getGold() {
        return gold;
    }

    public int getCitizens() {
        return citizens;
    }

    public int getFood() {
        return food;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void startProduction() {
        if (citizens > food) {
            int deficit = citizens - food;
            citizens -= deficit;
        }
        food -= citizens; // each citizen consumes one food
        gold += citizens * 2;
    }

    public void investFood(int goldAmount) {
        if (goldAmount > gold) {
            throw new InvalidDataException("Not enough gold");
        }
        gold -= goldAmount;
        food += goldAmount * 2;
    }

    public void investCitizens(int goldAmount) {
        if (goldAmount > gold) {
            throw new InvalidDataException("Not enough gold");
        }
        gold -= goldAmount;
        citizens += goldAmount;
    }

    public void addGold(int amount) {
        gold += amount;
    }

    public void removeGold(int amount) {
        gold -= amount;
    }

    public void addCitizens(int amount) {
        citizens += amount;
    }

    public void removeCitizens(int amount) {
        citizens -= amount;
    }

    public KingdomDTO toDTO() {
        return new KingdomDTO(id, createdAt, gold, citizens, food);
    }
}

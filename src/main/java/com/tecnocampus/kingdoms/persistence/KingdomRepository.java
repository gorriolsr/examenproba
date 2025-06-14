package com.tecnocampus.kingdoms.persistence;

import com.tecnocampus.kingdoms.domain.Kingdom;

import java.util.List;

public interface KingdomRepository {
    Kingdom save(Kingdom kingdom);
    Kingdom findById(String id);
    void delete(String id);
    List<Kingdom> findAll();
    Kingdom getRichest();
    void update(Kingdom kingdom);
}

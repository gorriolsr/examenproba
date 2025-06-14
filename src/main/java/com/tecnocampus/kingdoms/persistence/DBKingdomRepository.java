package com.tecnocampus.kingdoms.persistence;

import com.tecnocampus.kingdoms.domain.Kingdom;
import com.tecnocampus.outlaws.utilities.NotFoundException;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Repository
@Profile("db-h2")
public class DBKingdomRepository implements KingdomRepository {
    private final JdbcClient jdbcClient;

    public DBKingdomRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    private Kingdom map(ResultSet rs, int rowNum) throws SQLException {
        return new Kingdom(rs);
    }

    @Override
    public Kingdom save(Kingdom kingdom) {
        var params = Map.<String, Object>of(
                "id", kingdom.getId(),
                "gold", kingdom.getGold(),
                "citizens", kingdom.getCitizens(),
                "food", kingdom.getFood(),
                "createdAt", Timestamp.valueOf(kingdom.getCreatedAt())
        );
        jdbcClient.sql("INSERT INTO KINGDOMS(id,gold,citizens,food,created_at) VALUES (:id,:gold,:citizens,:food,:createdAt)")
                .params(params)
                .update();
        return kingdom;
    }

    @Override
    public Kingdom findById(String id) {
        return jdbcClient.sql("SELECT * FROM KINGDOMS WHERE id = :id")
                .param("id", id)
                .query(this::map)
                .optional().orElseThrow(() -> new NotFoundException("Kingdom not found"));
    }

    @Override
    public void delete(String id) {
        jdbcClient.sql("DELETE FROM KINGDOMS WHERE id=:id")
                .param("id", id)
                .update();
    }

    @Override
    public List<Kingdom> findAll() {
        return jdbcClient.sql("SELECT * FROM KINGDOMS")
                .query(this::map).list();
    }

    @Override
    public Kingdom getRichest() {
        return jdbcClient.sql("SELECT * FROM KINGDOMS ORDER BY gold DESC LIMIT 1")
                .query(this::map)
                .optional().orElseThrow(() -> new NotFoundException("No kingdoms"));
    }

    @Override
    public void update(Kingdom kingdom) {
        jdbcClient.sql("UPDATE KINGDOMS SET gold=:gold,citizens=:citizens,food=:food WHERE id=:id")
                .param("gold", kingdom.getGold())
                .param("citizens", kingdom.getCitizens())
                .param("food", kingdom.getFood())
                .param("id", kingdom.getId())
                .update();
    }
}

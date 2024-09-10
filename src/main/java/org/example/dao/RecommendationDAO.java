package org.example.dao;

import org.example.Recommendation;
import org.example.User;

import java.sql.SQLException;
import java.util.List;

public interface RecommendationDAO {
    void create(Recommendation recommendation) throws SQLException;
    Recommendation read(User user) throws SQLException;
    void update(Recommendation recommendation) throws SQLException;
    void delete(User user) throws SQLException;
    List<Recommendation> readAll() throws SQLException;
}

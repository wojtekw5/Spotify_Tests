package org.example.dao;

import org.example.Review;

import java.sql.SQLException;
import java.util.List;

public interface ReviewDAO {
    void create(Review review) throws SQLException;
    Review read(int id) throws SQLException;
    List<Review> readAll() throws SQLException;
    void update(Review review) throws SQLException;
    void delete(int id) throws SQLException;
}

package org.example.dao;

import org.example.Genre;

import java.sql.SQLException;
import java.util.List;

public interface GenreDAO {
    void create(Genre genre) throws SQLException;
    Genre read(int id) throws SQLException;
    List<Genre> readAll() throws SQLException;
    void update(Genre genre) throws SQLException;
    void delete(int id) throws SQLException;
}

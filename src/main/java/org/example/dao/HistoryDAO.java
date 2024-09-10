package org.example.dao;

import org.example.History;
import org.example.User;

import java.sql.SQLException;
import java.util.List;

public interface HistoryDAO {
    void create(History history) throws SQLException;
    History read(User user) throws SQLException;
    void update(History history) throws SQLException;
    void delete(User user) throws SQLException;
    List<History> readAll() throws SQLException;
}

package org.example.dao;

import org.example.Genre;
import org.example.dao.GenreDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenreDAOImpl implements GenreDAO {
    private final Connection connection;

    public GenreDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Genre genre) throws SQLException {
        String sql = "INSERT INTO genres (id, name) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, genre.getId());
            statement.setString(2, genre.getName());
            statement.executeUpdate();
        }
    }

    @Override
    public Genre read(int id) throws SQLException {
        String sql = "SELECT * FROM genres WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Genre(
                            resultSet.getInt("id"),
                            resultSet.getString("name")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Genre> readAll() throws SQLException {
        List<Genre> genres = new ArrayList<>();
        String sql = "SELECT * FROM genres";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                genres.add(new Genre(
                        resultSet.getInt("id"),
                        resultSet.getString("name")
                ));
            }
        }
        return genres;
    }

    @Override
    public void update(Genre genre) throws SQLException {
        String sql = "UPDATE genres SET name = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, genre.getName());
            statement.setInt(2, genre.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM genres WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}

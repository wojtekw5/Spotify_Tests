package org.example.dao;

import org.example.Artist;
import org.example.Recommendation;
import org.example.Song;
import org.example.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecommendationDAOImpl implements RecommendationDAO {
    private final Connection connection;

    public RecommendationDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Recommendation recommendation) throws SQLException {
        String sql = "INSERT INTO recommendations (user_id, song_id) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Song song : recommendation.getRecommendedSongs()) {
                statement.setInt(1, recommendation.getUser().getId());
                statement.setInt(2, song.getId());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    @Override
    public Recommendation read(User user) throws SQLException {
        String sql = "SELECT song_id FROM recommendations WHERE user_id = ?";
        Recommendation recommendation = new Recommendation(user);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int songId = resultSet.getInt("song_id");
                    Song song = getSongById(songId);
                    recommendation.getRecommendedSongs().add(song);
                }
            }
        }
        return recommendation;
    }

    @Override
    public void update(Recommendation recommendation) throws SQLException {
        delete(recommendation.getUser());
        create(recommendation);
    }

    @Override
    public void delete(User user) throws SQLException {
        String sql = "DELETE FROM recommendations WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public List<Recommendation> readAll() throws SQLException {
        List<Recommendation> recommendations = new ArrayList<>();
        String sql = "SELECT DISTINCT user_id FROM recommendations";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                User user = getUserById(userId);
                Recommendation recommendation = read(user);
                recommendations.add(recommendation);
            }
        }
        return recommendations;
    }

    public Song getSongById(int id) throws SQLException {
        String sql = "SELECT * FROM songs WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int songId = resultSet.getInt("id");
                    String title = resultSet.getString("title");
                    String artist = resultSet.getString("artist");
                    String album = resultSet.getString("album");
                    int duration = resultSet.getInt("duration");
                    String genre = resultSet.getString("genre");
                    int playCount = resultSet.getInt("play_count");

                    return new Song(songId, title, artist, album, duration, genre, playCount);
                } else {
                    return null;
                }
            }
        }
    }

    public User getUserById(int id) throws SQLException {
        String sql = "SELECT id, name FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new User(
                            resultSet.getInt("id"),
                            resultSet.getString("name")
                    );
                }
            }
        }
        return null;
    }
}

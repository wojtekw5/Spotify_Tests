package org.example.dao;

import org.example.Review;
import org.example.Song;
import org.example.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAOImpl implements ReviewDAO {
    private final Connection connection;

    public ReviewDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Review review) throws SQLException {
        String sql = "INSERT INTO reviews (id, user_id, song_id, review_text, rating) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, review.getId());
            statement.setInt(2, review.getUser().getId());
            statement.setInt(3, review.getSong().getId());
            statement.setString(4, review.getReviewText());
            statement.setInt(5, review.getRating());
            statement.executeUpdate();
        }
    }

    @Override
    public Review read(int id) throws SQLException {
        String sql = "SELECT * FROM reviews WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = getUserById(resultSet.getInt("user_id"));
                    Song song = getSongById(resultSet.getInt("song_id"));
                    return new Review(
                            resultSet.getInt("id"),
                            user,
                            song,
                            resultSet.getString("review_text"),
                            resultSet.getInt("rating")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Review> readAll() throws SQLException {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM reviews";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                User user = getUserById(resultSet.getInt("user_id"));
                Song song = getSongById(resultSet.getInt("song_id"));
                Review review = new Review(
                        resultSet.getInt("id"),
                        user,
                        song,
                        resultSet.getString("review_text"),
                        resultSet.getInt("rating")
                );
                reviews.add(review);
            }
        }
        return reviews;
    }

    @Override
    public void update(Review review) throws SQLException {
        String sql = "UPDATE reviews SET review_text = ?, rating = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, review.getReviewText());
            statement.setInt(2, review.getRating());
            statement.setInt(3, review.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM reviews WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
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
}

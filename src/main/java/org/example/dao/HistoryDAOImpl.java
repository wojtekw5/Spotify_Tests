package org.example.dao;

import org.example.History;
import org.example.Song;
import org.example.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryDAOImpl implements HistoryDAO {
    private final Connection connection;

    public HistoryDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(History history) throws SQLException {
        String sql = "INSERT INTO history (user_id, song_id) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Song song : history.getPlayedSongs()) {
                statement.setInt(1, history.getUser().getId());
                statement.setInt(2, song.getId());
                statement.executeUpdate();
            }

        }
    }

    @Override
    public History read(User user) throws SQLException {
        String sql = "SELECT song_id FROM history WHERE user_id = ?";
        History history = new History(user);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int songId = resultSet.getInt("song_id");
                    Song song = getSongById(songId);
                    history.getPlayedSongs().add(song);
                }
            }
        }
        return history;
    }

    @Override
    public void update(History history) throws SQLException {
        delete(history.getUser());
        create(history);
    }

    @Override
    public void delete(User user) throws SQLException {
        String sql = "DELETE FROM history WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public List<History> readAll() throws SQLException {
        List<History> histories = new ArrayList<>();
        String sql = "SELECT DISTINCT user_id FROM history";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                User user = getUserById(userId);
                History history = read(user);
                histories.add(history);
            }
        }
        return histories;
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
        String sql = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int userId = resultSet.getInt("id");
                    String username = resultSet.getString("username");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("password");

                    return new User(userId, username, email, password);
                } else {
                    return null;
                }
            }
        }
    }
}

package org.example.dao;

import org.example.Song;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SongDAOImpl implements SongDAO {
    private Connection connection;

    public SongDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Song song) throws SQLException {
        String sql = "INSERT INTO songs (id, title, artist, album, duration, genre, playCount) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, song.getId());
            statement.setString(2, song.getTitle());
            statement.setString(3, song.getArtist());
            statement.setString(4, song.getAlbum());
            statement.setInt(5, song.getDuration());
            statement.setString(6, song.getGenre());
            statement.setInt(7, song.getPlayCount());
            statement.executeUpdate();
        }
    }

    @Override
    public Song read(int id) throws SQLException {
        String sql = "SELECT * FROM songs WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Song(
                            resultSet.getInt("id"),
                            resultSet.getString("title"),
                            resultSet.getString("artist"),
                            resultSet.getString("album"),
                            resultSet.getInt("duration"),
                            resultSet.getString("genre"),
                            resultSet.getInt("playCount")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Song> readAll() throws SQLException {
        List<Song> songs = new ArrayList<>();
        String sql = "SELECT * FROM songs";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                songs.add(new Song(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("artist"),
                        resultSet.getString("album"),
                        resultSet.getInt("duration"),
                        resultSet.getString("genre"),
                        resultSet.getInt("playCount")
                ));
            }
        }
        return songs;
    }

    @Override
    public void update(Song song) throws SQLException {
        String sql = "UPDATE songs SET title = ?, artist = ?, album = ?, duration = ?, genre = ?, playCount = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, song.getTitle());
            statement.setString(2, song.getArtist());
            statement.setString(3, song.getAlbum());
            statement.setInt(4, song.getDuration());
            statement.setString(5, song.getGenre());
            statement.setInt(6, song.getPlayCount());
            statement.setInt(7, song.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM songs WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}

package org.example.dao;

import org.example.Playlist;
import org.example.Song;
import org.example.User;
import org.example.dao.PlaylistDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAOImpl implements PlaylistDAO {
    private final Connection connection;

    public PlaylistDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Playlist playlist) throws SQLException {
        String sql = "INSERT INTO playlists (id, name, ownerId) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, playlist.getId());
            statement.setString(2, playlist.getName());
            statement.setInt(3, playlist.getOwner().getId());
            statement.executeUpdate();
        }
    }

    @Override
    public Playlist read(int id) throws SQLException {
        String sql = "SELECT * FROM playlists WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int ownerId = resultSet.getInt("ownerId");
                    String ownerName = resultSet.getString("ownerName");
                    String ownerEmail = resultSet.getString("ownerEmail");
                    String ownerPassword = resultSet.getString("ownerPassword");
                    User owner = new User(ownerId, ownerName, ownerEmail, ownerPassword);
                    Playlist playlist = new Playlist(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            owner
                    );

                    // pobieranie utworów dla playlisty
                    String songSql = "SELECT s.* FROM songs s JOIN playlist_songs ps ON s.id = ps.song_id WHERE ps.playlist_id = ?";
                    try (PreparedStatement songStatement = connection.prepareStatement(songSql)) {
                        songStatement.setInt(1, id);
                        try (ResultSet songResultSet = songStatement.executeQuery()) {
                            while (songResultSet.next()) {
                                Song song = new Song(
                                        songResultSet.getInt("id"),
                                        songResultSet.getString("title"),
                                        songResultSet.getString("artist"),
                                        songResultSet.getString("album"),
                                        songResultSet.getInt("duration"),
                                        songResultSet.getString("genre")
                                );
                                playlist.addSong(song);
                            }
                        }
                    }
                    return playlist;
                }
            }
        }
        return null;
    }

    @Override
    public List<Playlist> readAll() throws SQLException {
        List<Playlist> playlists = new ArrayList<>();
        String sql = "SELECT * FROM playlists";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int ownerId = resultSet.getInt("ownerId");
                String ownerName = resultSet.getString("ownerName");
                String ownerEmail = resultSet.getString("ownerEmail");
                String ownerPassword = resultSet.getString("ownerPassword");
                User owner = new User(ownerId, ownerName, ownerEmail, ownerPassword);
                Playlist playlist = new Playlist(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        owner
                );

                // Pobieranie utworów dla playlisty
                String songSql = "SELECT s.* FROM songs s JOIN playlist_songs ps ON s.id = ps.song_id WHERE ps.playlist_id = ?";
                try (PreparedStatement songStatement = connection.prepareStatement(songSql)) {
                    songStatement.setInt(1, resultSet.getInt("id"));
                    try (ResultSet songResultSet = songStatement.executeQuery()) {
                        while (songResultSet.next()) {
                            Song song = new Song(
                                    songResultSet.getInt("id"),
                                    songResultSet.getString("title"),
                                    songResultSet.getString("artist"),
                                    songResultSet.getString("album"),
                                    songResultSet.getInt("duration"),
                                    songResultSet.getString("genre")
                            );
                            playlist.addSong(song);
                        }
                    }
                }
                playlists.add(playlist);
            }
        }
        return playlists;
    }

    @Override
    public void update(Playlist playlist) throws SQLException {
        String sql = "UPDATE playlists SET name = ?, ownerId = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, playlist.getName());
            statement.setInt(2, playlist.getOwner().getId());
            statement.setInt(3, playlist.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM playlists WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    public void addSongToPlaylist(int playlistId, Song song) throws SQLException {
        String sql = "INSERT INTO playlist_songs (playlist_id, song_id) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, playlistId);
            statement.setInt(2, song.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void removeSongFromPlaylist(int playlistId, Song song) throws SQLException {
        String sql = "DELETE FROM playlist_songs WHERE playlist_id = ? AND song_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, playlistId);
            statement.setInt(2, song.getId());
            statement.executeUpdate();
        }
    }
}

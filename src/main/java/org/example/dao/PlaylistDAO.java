package org.example.dao;

import org.example.Playlist;
import org.example.Song;

import java.sql.SQLException;
import java.util.List;

public interface PlaylistDAO {
    void create(Playlist playlist) throws SQLException;
    Playlist read(int id) throws SQLException;
    List<Playlist> readAll() throws SQLException;
    void update(Playlist playlist) throws SQLException;
    void delete(int id) throws SQLException;
    void addSongToPlaylist(int playlistId, Song song) throws SQLException;
    void removeSongFromPlaylist(int playlistId, Song song) throws SQLException;
}

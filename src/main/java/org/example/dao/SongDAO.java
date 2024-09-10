package org.example.dao;

import org.example.Song;

import java.sql.SQLException;
import java.util.List;

public interface SongDAO {
    void create(Song song) throws SQLException;
    Song read(int id) throws SQLException;
    List<Song> readAll() throws SQLException;
    void update(Song song) throws SQLException;
    void delete(int id) throws SQLException;
}

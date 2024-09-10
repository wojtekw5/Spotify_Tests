package org.example.dao;

import org.example.User;
import org.example.Song;
import org.example.Playlist;

import java.util.List;

public interface UserDAO {
    void create(User user);
    User read(int id);
    void update(User user);
    void delete(int id);
    List<User> readAll();

    void addFavoriteSong(int userId, Song song);
    void addPlaylist(int userId, Playlist playlist);
    void addPlayback(int userId, Song song);
}

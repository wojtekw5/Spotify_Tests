package org.example.dao;
import org.example.Album;
import org.example.Artist;

import java.util.List;

public interface AlbumDAO {
    void create(Album album);
    Album read(int id);
    void update(Album album);
    void delete(int id);
    List<Album> getAllAlbums();
}


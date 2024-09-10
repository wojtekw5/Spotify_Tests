package org.example.dao;

import org.example.Artist;

import java.util.List;

public interface ArtistDAO {
    void create(Artist artist);
    Artist read(int id);
    void update(Artist artist);
    void delete(int id);
    List<Artist> getAllArtists();
}

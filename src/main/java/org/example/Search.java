package org.example;

import java.util.ArrayList;
import java.util.List;

public class Search {
    private String query;
    private List<Song> foundSongs;
    private List<Album> foundAlbums;
    private List<Artist> foundArtists;
    private List<Playlist> foundPlaylists;

    public Search(String query) {
        this.query = query;
        this.foundSongs = new ArrayList<>();
        this.foundAlbums = new ArrayList<>();
        this.foundArtists = new ArrayList<>();
        this.foundPlaylists = new ArrayList<>();
    }

    public String getQuery() {
        return query;
    }

    public List<Song> getFoundSongs() {
        return foundSongs;
    }

    public List<Album> getFoundAlbums() {
        return foundAlbums;
    }

    public List<Artist> getFoundArtists() {
        return foundArtists;
    }

    public List<Playlist> getFoundPlaylists() {
        return foundPlaylists;
    }

    public void addFoundSongs(List<Song> songs) {
        foundSongs.addAll(songs);
    }

    public void addFoundAlbums(List<Album> albums) {
        foundAlbums.addAll(albums);
    }

    public void addFoundArtists(List<Artist> artists) {
        foundArtists.addAll(artists);
    }

    public void addFoundPlaylists(List<Playlist> playlists) {
        foundPlaylists.addAll(playlists);
    }

    public void performSearch() {
    }
}

package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Artist {
    private int id;
    private String name;
    private List<Album> albums;
    private List<Song> songs;

    public Artist(int id, String name) {
        this.id = id;
        this.name = name;
        this.albums = new ArrayList<>();
        this.songs = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void addAlbum(Album album) {
        if (!albums.contains(album)) {
            albums.add(album);
        }
    }

    public void addSong(Song song) {
        if (!songs.contains(song)) {
            songs.add(song);
        }
    }

    public List<Song> getTopSongs() {
        return songs.stream()
                .sorted(Comparator.comparingInt(Song::getPlayCount).reversed()) // sortowanie utworów na podstawie liczby odtworzeń
                .limit(10) // Zwracamy top 10 utworów
                .collect(Collectors.toList());
    }
}

package org.example;
import java.util.ArrayList;
import java.util.List;

public class Genre {
    private int id;
    private String name;
    private List<Song> songs;
    private List<Artist> artists;

    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
        this.songs = new ArrayList<>();
        this.artists = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void addSong(Song song) {
        if (!songs.contains(song)) {
            songs.add(song);
        }
    }

    public void addArtist(Artist artist) {
        if (!artists.contains(artist)) {
            artists.add(artist);
        }
    }

    public List<Song> getTopSongs() {
        // logika wyboru najlepszych, np. na podstawie liczby odtworzeń
        return songs;
    }
}

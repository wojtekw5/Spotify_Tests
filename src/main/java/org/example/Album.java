package org.example;
import java.util.ArrayList;
import java.util.List;

public class Album {
    private int id;
    private String title;
    private Artist artist;
    private int releaseYear;
    private List<Song> songs;

    public Album(int id, String title, Artist artist, int releaseYear) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.releaseYear = releaseYear;
        this.songs = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Artist getArtist() {
        return artist;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void addSong(Song song) {
        if (!songs.contains(song)) {
            songs.add(song);
        }
    }
}

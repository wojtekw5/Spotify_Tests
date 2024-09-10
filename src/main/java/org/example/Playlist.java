package org.example;
import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private int id;
    private String name;
    private User owner;
    private List<Song> songs;

    public Playlist(int id, String name, User owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.songs = new ArrayList<>();
    }

    public Playlist(String name, User owner) {
        this.name = name;
        this.owner = owner;
        this.songs = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getOwner() {
        return owner;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void addSong(Song song) {
        if (!songs.contains(song)) {
            songs.add(song);
        }
    }

    public void removeSong(Song song) {
        songs.remove(song);
    }
}


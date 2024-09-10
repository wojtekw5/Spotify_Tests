package org.example;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private List<Song> favoriteSongs;
    private List<Playlist> playlists;
    private List<Song> playbackHistory;
    private Subscription subscription;

    public User(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.favoriteSongs = new ArrayList<>();
        this.playlists = new ArrayList<>();
        this.playbackHistory = new ArrayList<>();
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<Song> getFavoriteSongs() {
        return favoriteSongs;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public void addFavoriteSong(Song song) {
        if (!favoriteSongs.contains(song)) {
            favoriteSongs.add(song);
        }
    }

    public Playlist createPlaylist(String name) {
        Playlist newPlaylist = new Playlist(name, this);
        playlists.add(newPlaylist);
        return newPlaylist;
    }

    public void addPlayback(Song song) {
        playbackHistory.add(song);
    }

    public List<Song> getPlaybackHistory() {
        return playbackHistory;
    }
}

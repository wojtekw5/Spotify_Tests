package org.example;
import java.util.ArrayList;
import java.util.List;

public class History {
    private User user;
    private List<Song> playedSongs;

    public History(User user) {
        this.user = user;
        this.playedSongs = new ArrayList<>();
    }

    public User getUser() {
        return user;
    }

    public List<Song> getPlayedSongs() {
        return playedSongs;
    }

    public void addPlayedSong(Song song) {
        playedSongs.add(song);
    }
}

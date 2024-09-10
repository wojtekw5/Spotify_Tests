package org.example;

import java.util.ArrayList;
import java.util.List;

public class Song {
    private int id;
    private String title;
    private String artist;
    private String album;
    private int duration; // w sekundach
    private String genre;
    private int playCount;
    private List<Review> reviews;

    // bez playCount
    public Song(int id, String title, String artist, String album, int duration, String genre) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.genre = genre;
        this.playCount = 0;
        this.reviews = new ArrayList<>();
    }

    // z playCount
    public Song(int id, String title, String artist, String album, int duration, String genre, int playCount) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.genre = genre;
        this.playCount = playCount;
        this.reviews = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public int getDuration() {
        return duration;
    }

    public String getGenre() {
        return genre;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void incrementPlayCount() {
        this.playCount++;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }

    public String getSongInfo() {
        return String.format("Title: %s, Artist: %s, Album: %s, Duration: %d seconds, Genre: %s, PlayCount: %d",
                title, artist, album, duration, genre, playCount);
    }
}

package org.example;
public class Review {
    private int id;
    private User user;
    private Song song;
    private String reviewText;
    private int rating;

    public Review(int id, User user, Song song, String reviewText, int rating) {
        this.id = id;
        this.user = user;
        this.song = song;
        this.reviewText = reviewText;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Song getSong() {
        return song;
    }

    public String getReviewText() {
        return reviewText;
    }

    public int getRating() {
        return rating;
    }

    public void editReview(String newText) {
        this.reviewText = newText;
    }

    public void updateRating(int newRating) {
        this.rating = newRating;
    }
}

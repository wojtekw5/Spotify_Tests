package steps;

import org.example.Review;
import org.example.User;
import org.example.Song;
import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

public class ReviewManagementSteps {

    private User user;
    private Song song;
    private Review review;

    @Given("We have user {string} with id: {int}")
    public void we_have_user_with_id_review_management(String name, int id) {
        user = new User(id, name, name.toLowerCase() + "@example.com", "password123");
    }

    @Given("{string} has listened to song {int} titled {string}")
    public void user_has_listened_to_song_titled(String userName, int songId, String songTitle) {
        song = new Song(songId, songTitle, userName, "Album A", 210, "Pop");
    }

    @When("{string} writes a review {string} with rating {int} for song {int}")
    public void user_writes_a_review_with_rating_for_song(String userName, String reviewText, int rating, int songId) {
        review = new Review(1, user, song, reviewText, rating);
        song.addReview(review);
    }

    @Then("song {int} has a review by user {int} with text {string} and rating {int}")
    public void song_has_a_review_by_user_with_text_and_rating(int songId, int userId, String reviewText, int rating) {
        assertTrue(song.getReviews().stream().anyMatch(r -> r.getUser().getId() == userId && r.getReviewText().equals(reviewText) && r.getRating() == rating));
    }

    @Given("{string} has written a review for song {int} titled {string}")
    public void has_written_a_review_for_song_titled(String userName, int songId, String songTitle) {
        song = new Song(songId, songTitle, userName, "Album B", 200, "Ambient");
        review = new Review(1, user, song, "Amazing track!", 5);
        song.addReview(review);
    }

    @When("{string} edits her review for song {int} to {string} with rating {int}")
    public void user_edits_her_review_for_song_to_with_rating(String userName, int songId, String reviewText, int rating) {
        review.editReview(reviewText);
        review.updateRating(rating);
    }
}

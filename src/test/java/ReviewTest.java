import org.example.Review;
import org.example.User;
import org.example.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReviewTest {

    private Review review;
    private User user;
    private Song song;

    @BeforeEach
    public void setUp() {
        user = mock(User.class);
        song = mock(Song.class);
        review = new Review(1, user, song, "Great song!", 5);
    }

    @Test
    public void testGetId() {
        assertEquals(1, review.getId());
    }

    @Test
    public void testGetUser() {
        assertEquals(user, review.getUser());
    }

    @Test
    public void testGetSong() {
        assertEquals(song, review.getSong());
    }

    @Test
    public void testGetReviewText() {
        assertEquals("Great song!", review.getReviewText());
    }

    @Test
    public void testGetRating() {
        assertEquals(5, review.getRating());
    }

    @Test
    public void testEditReview() {
        review.editReview("Amazing song!");
        assertEquals("Amazing song!", review.getReviewText());
    }

    @Test
    public void testUpdateRating() {
        review.updateRating(4);
        assertEquals(4, review.getRating());
    }
}

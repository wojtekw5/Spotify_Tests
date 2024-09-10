import org.example.Song;
import org.example.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class SongTest {

    private Song song;

    @BeforeEach
    public void setUp() {
        song = new Song(1, "Last Resort", "Papa Roach", "Infest", 210, "Hard Rock");
    }

    @Test
    public void testSongCreation() {
        assertEquals(1, song.getId());
        assertEquals("Last Resort", song.getTitle());
        assertEquals("Papa Roach", song.getArtist());
        assertEquals("Infest", song.getAlbum());
        assertEquals(210, song.getDuration());
        assertEquals("Hard Rock", song.getGenre());
        assertEquals(0, song.getPlayCount());
        assertTrue(song.getReviews().isEmpty());
    }

    @Test
    public void testGetId() {
        assertEquals(1, song.getId());
    }

    @Test
    public void testGetTitle() {
        assertEquals("Last Resort", song.getTitle());
    }

    @Test
    public void testGetArtist() {
        assertEquals("Papa Roach", song.getArtist());
    }

    @Test
    public void testGetAlbum() {
        assertEquals("Infest", song.getAlbum());
    }

    @Test
    public void testGetDuration() {
        assertEquals(210, song.getDuration());
    }

    @Test
    public void testGetGenre() {
        assertEquals("Hard Rock", song.getGenre());
    }

    @Test
    public void testGetPlayCount() {
        assertEquals(0, song.getPlayCount());
        song.incrementPlayCount();
        assertEquals(1, song.getPlayCount());
    }

    @Test
    public void testIncrementPlayCount() {
        song.incrementPlayCount();
        song.incrementPlayCount();
        assertEquals(2, song.getPlayCount());
    }

    @Test
    public void testGetReviews() {
        assertTrue(song.getReviews().isEmpty());
        Review review = new Review(1, null, song, "Great song!", 5);
        song.addReview(review);
        List<Review> reviews = song.getReviews();
        assertEquals(1, reviews.size());
        assertEquals(review, reviews.get(0));
    }

    @Test
    public void testAddReview() {
        Review review = new Review(1, null, song, "Great song!", 5);
        song.addReview(review);
        assertEquals(1, song.getReviews().size());
        assertEquals(review, song.getReviews().get(0));
    }

    @Test
    public void testGetSongInfo() {
        String expectedInfo = "Title: Last Resort, Artist: Papa Roach, Album: Infest, Duration: 210 seconds, Genre: Hard Rock, PlayCount: 0";
        assertEquals(expectedInfo, song.getSongInfo());
    }
}

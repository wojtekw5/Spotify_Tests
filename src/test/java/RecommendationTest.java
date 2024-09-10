import org.example.Recommendation;
import org.example.User;
import org.example.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.ArrayList;

public class RecommendationTest {

    private Recommendation recommendation;
    private User user;
    private Song song1;
    private Song song2;
    private Song song3;
    private Song song4;
    private List<Song> allSongs;

    @BeforeEach
    public void setUp() {
        user = mock(User.class);
        recommendation = new Recommendation(user);

        song1 = new Song(1, "Song 1", "Artist 1", "Album 1", 180, "Pop", 100);
        song2 = new Song(2, "Song 2", "Artist 2", "Album 2", 200, "Pop", 200);
        song3 = new Song(3, "Song 3", "Artist 3", "Album 3", 240, "Rock", 150);
        song4 = new Song(4, "Song 4", "Artist 4", "Album 4", 210, "Pop", 250);

        allSongs = new ArrayList<>();
        allSongs.add(song1);
        allSongs.add(song2);
        allSongs.add(song3);
        allSongs.add(song4);

        List<Song> favoriteSongs = new ArrayList<>();
        favoriteSongs.add(song1); // Gatunek "Pop"
        when(user.getFavoriteSongs()).thenReturn(favoriteSongs);
    }

    @Test
    public void testGetUser() {
        assertEquals(user, recommendation.getUser());
    }

    @Test
    public void testGetRecommendedSongsInitiallyEmpty() {
        assertTrue(recommendation.getRecommendedSongs().isEmpty());
    }

    @Test
    public void testGenerateRecommendations() {
        recommendation.generateRecommendations(allSongs);

        assertEquals(3, recommendation.getRecommendedSongs().size());

        assertTrue(recommendation.getRecommendedSongs().contains(song1));
        assertTrue(recommendation.getRecommendedSongs().contains(song2));
        assertTrue(recommendation.getRecommendedSongs().contains(song4));

        assertEquals(song4, recommendation.getRecommendedSongs().get(0));
        assertEquals(song2, recommendation.getRecommendedSongs().get(1));
        assertEquals(song1, recommendation.getRecommendedSongs().get(2));
    }
}

import org.example.History;
import org.example.User;
import org.example.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

public class HistoryTest {

    private History history;
    private User user;
    private Song song1;
    private Song song2;

    @BeforeEach
    public void setUp() {
        user = mock(User.class);
        history = new History(user);
        song1 = mock(Song.class);
        song2 = mock(Song.class);
    }

    @Test
    public void testGetUser() {
        assertEquals(user, history.getUser());
    }

    @Test
    public void testGetPlayedSongs() {
        history.addPlayedSong(song1);
        history.addPlayedSong(song2);
        history.addPlayedSong(song1);
        assertEquals(3,history.getPlayedSongs().size());
    }

    @Test
    public void testAddPlayedSong() {
        history.addPlayedSong(song1);
        history.addPlayedSong(song2);
        assertEquals(2, history.getPlayedSongs().size());
        assertTrue(history.getPlayedSongs().contains(song1));
        assertTrue(history.getPlayedSongs().contains(song2));
    }
}

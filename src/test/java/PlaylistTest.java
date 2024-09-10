import org.example.Playlist;
import org.example.User;
import org.example.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

public class PlaylistTest {

    private Playlist playlist;
    private User user;
    private Song song1;
    private Song song2;

    @BeforeEach
    public void setUp() {
        user = mock(User.class);
        playlist = new Playlist(1, "My Playlist", user);
        song1 = mock(Song.class);
        song2 = mock(Song.class);
    }

    @Test
    public void testGetId() {
        assertEquals(1, playlist.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("My Playlist", playlist.getName());
    }

    @Test
    public void testGetOwner() {
        assertEquals(user, playlist.getOwner());
    }

    @Test
    public void testGetSongs() {
        playlist.addSong(song1);
        playlist.addSong(song2);
        assertEquals(2,playlist.getSongs().size());}

    @Test
    public void testAddSong() {
        playlist.addSong(song1);
        playlist.addSong(song2);
        assertEquals(2, playlist.getSongs().size());
        assertTrue(playlist.getSongs().contains(song1));
        assertTrue(playlist.getSongs().contains(song2));
    }

    @Test
    public void testAddDuplicateSong() {
        playlist.addSong(song1);
        playlist.addSong(song1);
        assertEquals(1, playlist.getSongs().size());
        assertTrue(playlist.getSongs().contains(song1));
    }

    @Test
    public void testRemoveSong() {
        playlist.addSong(song1);
        playlist.addSong(song2);
        playlist.removeSong(song1);
        assertEquals(1, playlist.getSongs().size());
        assertFalse(playlist.getSongs().contains(song1));
        assertTrue(playlist.getSongs().contains(song2));
    }

    @Test
    public void testRemoveNonexistentSong() {
        playlist.addSong(song1);
        playlist.removeSong(song2);
        assertEquals(1, playlist.getSongs().size());
        assertTrue(playlist.getSongs().contains(song1));
        assertFalse(playlist.getSongs().contains(song2));
    }
}

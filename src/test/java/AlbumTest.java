import org.example.Album;
import org.example.Artist;
import org.example.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

public class AlbumTest {

    private Album album;
    private Artist artist;
    private Song song1;
    private Song song2;

    @BeforeEach
    public void setUp() {
        artist = mock(Artist.class);
        song1 = mock(Song.class);
        song2 = mock(Song.class);

        album = new Album(1, "Infest", artist, 2001);
    }

    @Test
    public void testGetId() {
        assertEquals(1, album.getId());
    }

    @Test
    public void testGetTitle() {
        assertEquals("Infest", album.getTitle());
    }

    @Test
    public void testGetArtist() {
        assertEquals(artist, album.getArtist());
    }

    @Test
    public void testGetReleaseYear() {
        assertEquals(2001, album.getReleaseYear());
    }

    @Test
    public void testGetSongs() {
        album.addSong(song1);
        album.addSong(song2);
        assertEquals(2,album.getSongs().size());
        assertTrue(album.getSongs().contains(song1));
        assertTrue(album.getSongs().contains(song2));
    }

    @Test
    public void testAddSong() {
        album.addSong(song1);
        album.addSong(song2);
        assertEquals(2, album.getSongs().size());
        assertTrue(album.getSongs().contains(song1));
        assertTrue(album.getSongs().contains(song2));
    }

    @Test
    public void testAddDuplicateSong() {
        album.addSong(song1);
        album.addSong(song1);
        assertEquals(1, album.getSongs().size());
        assertTrue(album.getSongs().contains(song1));
    }
}

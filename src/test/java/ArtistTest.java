import org.example.Artist;
import org.example.Album;
import org.example.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

public class ArtistTest {

    private Artist artist;
    private Album album1;
    private Album album2;
    private Song song1;
    private Song song2;

    @BeforeEach
    public void setUp() {
        artist = new Artist(1, "Papa Roach");
        album1 = mock(Album.class);
        album2 = mock(Album.class);
        song1 = mock(Song.class);
        song2 = mock(Song.class);
    }

    @Test
    public void testGetId() {
        assertEquals(1, artist.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("Papa Roach", artist.getName());
    }

    @Test
    public void testGetAlbums() {
        artist.addAlbum(album1);
        artist.addAlbum(album2);
        assertEquals(2,artist.getAlbums().size());
        assertTrue(artist.getAlbums().contains(album1));
        assertTrue(artist.getAlbums().contains(album2));

    }

    @Test
    public void testGetSongs() {
        artist.addSong(song1);
        artist.addSong(song2);
        assertEquals(2,artist.getSongs().size());
        assertTrue(artist.getSongs().contains(song1));
        assertTrue(artist.getSongs().contains(song2));
    }

    @Test
    public void testAddAlbum() {
        artist.addAlbum(album1);
        artist.addAlbum(album2);
        assertEquals(2, artist.getAlbums().size());
        assertTrue(artist.getAlbums().contains(album1));
        assertTrue(artist.getAlbums().contains(album2));
    }

    @Test
    public void testAddDuplicateAlbum() {
        artist.addAlbum(album1);
        artist.addAlbum(album1);
        assertEquals(1, artist.getAlbums().size());
        assertTrue(artist.getAlbums().contains(album1));
    }

    @Test
    public void testAddSong() {
        artist.addSong(song1);
        artist.addSong(song2);
        assertEquals(2, artist.getSongs().size());
        assertTrue(artist.getSongs().contains(song1));
        assertTrue(artist.getSongs().contains(song2));
    }

    @Test
    public void testAddDuplicateSong() {
        artist.addSong(song1);
        artist.addSong(song1);
        assertEquals(1, artist.getSongs().size());
        assertTrue(artist.getSongs().contains(song1));
    }

    @Test
    public void testGetTopSongs() {
        artist.addSong(song1);
        artist.addSong(song2);
        assertEquals(2, artist.getTopSongs().size());
        assertTrue(artist.getTopSongs().contains(song1));
        assertTrue(artist.getTopSongs().contains(song2));
    }
}

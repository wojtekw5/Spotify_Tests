import org.example.Genre;
import org.example.Song;
import org.example.Artist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

public class GenreTest {

    private Genre genre;
    private Song song1;
    private Song song2;
    private Artist artist1;
    private Artist artist2;

    @BeforeEach
    public void setUp() {
        genre = new Genre(1, "Pop");
        song1 = mock(Song.class);
        song2 = mock(Song.class);
        artist1 = mock(Artist.class);
        artist2 = mock(Artist.class);
    }

    @Test
    public void testGetId() {
        assertEquals(1, genre.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("Pop", genre.getName());
    }

    @Test
    public void testGetSongs() {
        genre.addSong(song1);
        genre.addSong(song2);
        assertEquals(2,genre.getSongs().size());
        assertTrue(genre.getSongs().contains(song1));
        assertTrue(genre.getSongs().contains(song2));
    }

    @Test
    public void testGetArtists() {
        genre.addArtist(artist1);
        genre.addArtist(artist2);
        assertEquals(2,genre.getArtists().size());
        assertTrue(genre.getArtists().contains(artist2));
        assertTrue(genre.getArtists().contains(artist2));
    }

    @Test
    public void testAddSong() {
        genre.addSong(song1);
        genre.addSong(song2);
        assertEquals(2, genre.getSongs().size());
        assertTrue(genre.getSongs().contains(song1));
        assertTrue(genre.getSongs().contains(song2));
    }

    @Test
    public void testAddDuplicateSong() {
        genre.addSong(song1);
        genre.addSong(song1);
        assertEquals(1, genre.getSongs().size());
        assertTrue(genre.getSongs().contains(song1));
    }

    @Test
    public void testAddArtist() {
        genre.addArtist(artist1);
        genre.addArtist(artist2);
        assertEquals(2, genre.getArtists().size());
        assertTrue(genre.getArtists().contains(artist1));
        assertTrue(genre.getArtists().contains(artist2));
    }

    @Test
    public void testAddDuplicateArtist() {
        genre.addArtist(artist1);
        genre.addArtist(artist1);
        assertEquals(1, genre.getArtists().size());
        assertTrue(genre.getArtists().contains(artist1));
    }

    @Test
    public void testGetTopSongs() {
        genre.addSong(song1);
        genre.addSong(song2);
        assertEquals(2, genre.getTopSongs().size());
        assertTrue(genre.getTopSongs().contains(song1));
        assertTrue(genre.getTopSongs().contains(song2));
    }
}

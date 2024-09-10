import org.example.Search;
import org.example.Song;
import org.example.Album;
import org.example.Artist;
import org.example.Playlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.ArrayList;

public class SearchTest {

    private Search search;
    private Song song1;
    private Song song2;
    private Album album1;
    private Album album2;
    private Artist artist1;
    private Artist artist2;
    private Playlist playlist1;
    private Playlist playlist2;

    @BeforeEach
    public void setUp() {
        search = new Search("test query");
        song1 = mock(Song.class);
        song2 = mock(Song.class);
        album1 = mock(Album.class);
        album2 = mock(Album.class);
        artist1 = mock(Artist.class);
        artist2 = mock(Artist.class);
        playlist1 = mock(Playlist.class);
        playlist2 = mock(Playlist.class);

    }

    @Test
    public void testGetQuery() {
        assertEquals("test query", search.getQuery());
    }

    @Test
    public void testGetFoundSongs() {
        List<Song> foundSongs = new ArrayList<>();
        foundSongs.add(song1);
        foundSongs.add(song2);
        search.addFoundSongs(foundSongs);
        assertEquals(2, search.getFoundSongs().size());
    }

    @Test
    public void testGetFoundAlbums() {
        List<Album> foundAlbums = new ArrayList<>();
        foundAlbums.add(album1);
        search.addFoundAlbums(foundAlbums);
        assertEquals(1, search.getFoundAlbums().size());
    }

    @Test
    public void testGetFoundArtists() {
        List<Artist> foundArtists = new ArrayList<>();
        foundArtists.add(artist1);
        search.addFoundArtists(foundArtists);
        assertEquals(1, search.getFoundArtists().size());
    }

    @Test
    public void testGetFoundPlaylists() {
        List<Playlist> foundPlaylists = new ArrayList<>();
        foundPlaylists.add(playlist1);
        foundPlaylists.add(playlist2);
        search.addFoundPlaylists(foundPlaylists);
        assertEquals(2, search.getFoundPlaylists().size());
    }

    @Test
    public void testAddFoundSongs() {
        List<Song> foundSongs = new ArrayList<>();
        foundSongs.add(song1);
        foundSongs.add(song2);
        search.addFoundSongs(foundSongs);
        assertEquals(2, search.getFoundSongs().size());
        assertTrue(search.getFoundSongs().contains(song1));
        assertTrue(search.getFoundSongs().contains(song2));
    }

    @Test
    public void testAddFoundAlbums() {
        List<Album> foundAlbums = new ArrayList<>();
        foundAlbums.add(album1);
        foundAlbums.add(album2);
        search.addFoundAlbums(foundAlbums);
        assertEquals(2, search.getFoundAlbums().size());
        assertTrue(search.getFoundAlbums().contains(album1));
        assertTrue(search.getFoundAlbums().contains(album2));
    }

    @Test
    public void testAddFoundArtists() {
        List<Artist> foundArtists = new ArrayList<>();
        foundArtists.add(artist1);
        foundArtists.add(artist2);
        search.addFoundArtists(foundArtists);
        assertEquals(2, search.getFoundArtists().size());
        assertTrue(search.getFoundArtists().contains(artist1));
        assertTrue(search.getFoundArtists().contains(artist2));
    }

    @Test
    public void testAddFoundPlaylists() {
        List<Playlist> foundPlaylists = new ArrayList<>();
        foundPlaylists.add(playlist1);
        foundPlaylists.add(playlist2);
        search.addFoundPlaylists(foundPlaylists);
        assertEquals(2, search.getFoundPlaylists().size());
        assertTrue(search.getFoundPlaylists().contains(playlist1));
        assertTrue(search.getFoundPlaylists().contains(playlist2));
    }

    @Test
    public void testPerformSearch() {
        search.performSearch();
    }
}


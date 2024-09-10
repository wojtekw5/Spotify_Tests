import org.example.Song;
import org.example.Playlist;
import org.example.Subscription;
import org.example.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserTest {

    private User user;
    private Song song1;
    private Song song2;
    private Subscription subscription;

    @BeforeEach
    public void setUp() throws ParseException {
        user = new User(1, "Wojciech W", "wojciechw@email.com", "password123");
        song1 = new Song(1, "Last Resort", "Papa Roach", "Infest", 210, "Hard Rock");
        song2 = new Song(2, "Blood Brothers", "Papa Roach", "Infest", 180, "Rock");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date startDate = dateFormat.parse("2024-01-01");
        Date endDate = dateFormat.parse("2024-12-31");

        subscription = new Subscription(1,"student", startDate,endDate, "active");
    }

    @Test
    public void testGetId() {
        assertEquals(1, user.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("Wojciech W", user.getName());
    }

    @Test
    public void testGetEmail() {
        assertEquals("wojciechw@email.com", user.getEmail());
    }

    @Test
    public void testGetPassword() {
        assertEquals("password123", user.getPassword());
    }
    @Test
    void testGetSubscription() {
        user.setSubscription(subscription);

        assertEquals(subscription, user.getSubscription());
    }

    @Test
    public void testGetFavoriteSongs() {
        user.addFavoriteSong(song1);
        user.addFavoriteSong(song2);
        assertEquals(2,user.getFavoriteSongs().size());
        assertTrue(user.getFavoriteSongs().contains(song1));
        assertTrue(user.getFavoriteSongs().contains(song2));
    }

    @Test
    public void testGetPlaylists() {
        Playlist playlist1 = user.createPlaylist("Playlist 1");
        Playlist playlist2 = user.createPlaylist("Playlist 2");

        List<Playlist> playlists = user.getPlaylists();

        assertEquals(2, playlists.size());
        assertTrue(playlists.contains(playlist1));
        assertTrue(playlists.contains(playlist2));
    }


    @Test
    public void testAddFavoriteSong() {
        user.addFavoriteSong(song1);
        user.addFavoriteSong(song2);
        List<Song> favoriteSongs = user.getFavoriteSongs();
        assertEquals(2, favoriteSongs.size());
        assertTrue(favoriteSongs.contains(song1));
        assertTrue(favoriteSongs.contains(song2));
    }

    @Test
    public void testAddDuplicateFavoriteSong() {
        user.addFavoriteSong(song1);
        user.addFavoriteSong(song1);
        List<Song> favoriteSongs = user.getFavoriteSongs();
        assertEquals(1, favoriteSongs.size());
        assertTrue(favoriteSongs.contains(song1));
    }

    @Test
    public void testCreatePlaylist() {
        Playlist playlist = user.createPlaylist("My Playlist");
        assertNotNull(playlist);
        assertEquals("My Playlist", playlist.getName());
        assertEquals(user, playlist.getOwner());
        assertEquals(1, user.getPlaylists().size());
    }

    @Test
    public void testCreateMultiplePlaylists() {
        Playlist playlist1 = user.createPlaylist("Playlist 1");
        Playlist playlist2 = user.createPlaylist("Playlist 2");
        assertEquals(2, user.getPlaylists().size());
        assertTrue(user.getPlaylists().contains(playlist1));
        assertTrue(user.getPlaylists().contains(playlist2));
    }
}

package dao;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;
import java.util.List;

import org.example.Playlist;
import org.example.dao.PlaylistDAOImpl;
import org.example.Song;
import org.example.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

class PlaylistDAOImplTest {
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @Mock
    private Statement statement;

    private PlaylistDAOImpl playlistDAO;

    private Playlist playlist;

    private Song song;

    private int playlistId;

    @BeforeEach
    void setUp() throws Exception {
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
        statement = mock(Statement.class);
        playlistDAO = new PlaylistDAOImpl(connection);

        playlist = new Playlist(1, "My Playlist", new User(1, "Jan Nowak", "jann@email.com", "password"));
        song = new Song(1, "Song Title", "Artist Name", "Album Name", 200, "Genre");
        playlistId = 1;
    }

    @Test
    void testCreate() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        playlistDAO.create(playlist);

        verify(preparedStatement).setInt(1, playlist.getId());
        verify(preparedStatement).setString(2, playlist.getName());
        verify(preparedStatement).setInt(3, playlist.getOwner().getId());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testRead() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(playlistId);
        when(resultSet.getString("name")).thenReturn("My Playlist");
        when(resultSet.getInt("ownerId")).thenReturn(1);
        when(resultSet.getString("ownerName")).thenReturn("John Doe");
        when(resultSet.getString("ownerEmail")).thenReturn("john@example.com");
        when(resultSet.getString("ownerPassword")).thenReturn("password");

        PreparedStatement songStatement = mock(PreparedStatement.class); //zapytania sql o utwory
        ResultSet songResultSet = mock(ResultSet.class);
        when(connection.prepareStatement(contains("SELECT s.* FROM songs"))).thenReturn(songStatement);
        when(songStatement.executeQuery()).thenReturn(songResultSet);
        when(songResultSet.next()).thenReturn(true, false); // tylko jeden utwór
        when(songResultSet.getInt("id")).thenReturn(1);
        when(songResultSet.getString("title")).thenReturn("Song Title");
        when(songResultSet.getString("artist")).thenReturn("Artist Name");
        when(songResultSet.getString("album")).thenReturn("Album Name");
        when(songResultSet.getInt("duration")).thenReturn(200);
        when(songResultSet.getString("genre")).thenReturn("Genre");

        Playlist playlist = playlistDAO.read(playlistId);

        assertNotNull(playlist);
        assertEquals(playlistId, playlist.getId());
        assertEquals("My Playlist", playlist.getName());
        assertEquals(1, playlist.getOwner().getId());
        assertEquals("John Doe", playlist.getOwner().getName());

        List<Song> songs = playlist.getSongs();
        assertEquals(1, songs.size());
        Song song = songs.get(0);
        assertEquals(1, song.getId());
        assertEquals("Song Title", song.getTitle());
    }

    @Test
    void testReadAll() throws SQLException {
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false); // return true, potem false
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("name")).thenReturn("My Playlist");
        when(resultSet.getInt("ownerId")).thenReturn(1);
        when(resultSet.getString("ownerName")).thenReturn("Jan Nowak");
        when(resultSet.getString("ownerEmail")).thenReturn("jann@email.com");
        when(resultSet.getString("ownerPassword")).thenReturn("password");

        PreparedStatement songStatement = mock(PreparedStatement.class);
        ResultSet songResultSet = mock(ResultSet.class);
        when(connection.prepareStatement(contains("SELECT s.* FROM songs"))).thenReturn(songStatement);
        when(songStatement.executeQuery()).thenReturn(songResultSet);
        when(songResultSet.next()).thenReturn(true, false); // tylko jeden utwór
        when(songResultSet.getInt("id")).thenReturn(1);
        when(songResultSet.getString("title")).thenReturn("Song Title");
        when(songResultSet.getString("artist")).thenReturn("Artist Name");
        when(songResultSet.getString("album")).thenReturn("Album Name");
        when(songResultSet.getInt("duration")).thenReturn(200);
        when(songResultSet.getString("genre")).thenReturn("Genre");

        List<Playlist> playlists = playlistDAO.readAll();

        assertNotNull(playlists);
        assertEquals(1, playlists.size());
        Playlist playlist = playlists.get(0);
        assertEquals(1, playlist.getId());
        assertEquals("My Playlist", playlist.getName());
        assertEquals(1, playlist.getOwner().getId());

        List<Song> songs = playlist.getSongs();
        assertEquals(1, songs.size());
        Song song = songs.get(0);
        assertEquals(1, song.getId());
        assertEquals("Song Title", song.getTitle());
    }


    @Test
    void testUpdate() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        playlistDAO.update(playlist);

        verify(preparedStatement).setString(1, playlist.getName());
        verify(preparedStatement).setInt(2, playlist.getOwner().getId());
        verify(preparedStatement).setInt(3, playlist.getId());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testDelete() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        playlistDAO.delete(playlistId);

        verify(preparedStatement).setInt(1, playlistId);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testAddSongToPlaylist() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        playlistDAO.addSongToPlaylist(playlistId, song);

        verify(preparedStatement).setInt(1, playlistId);
        verify(preparedStatement).setInt(2, song.getId());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testRemoveSongFromPlaylist() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        playlistDAO.removeSongFromPlaylist(playlistId, song);

        verify(preparedStatement).setInt(1, playlistId);
        verify(preparedStatement).setInt(2, song.getId());
        verify(preparedStatement).executeUpdate();
    }
}

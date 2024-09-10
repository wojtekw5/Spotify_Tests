package dao;

import org.example.Song;
import org.example.dao.SongDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SongDAOImplTest {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private SongDAOImpl songDAO;

    @BeforeEach
    void setUp() throws SQLException {
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
        songDAO = new SongDAOImpl(connection);
    }

    @Test
    void testCreate() throws SQLException {
        String sql = "INSERT INTO songs (id, title, artist, album, duration, genre, playCount) VALUES (?, ?, ?, ?, ?, ?, ?)";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);

        Song song = new Song(1, "Title", "Artist", "Album", 300, "Genre");
        songDAO.create(song);

        verify(preparedStatement).setInt(1, song.getId());
        verify(preparedStatement).setString(2, song.getTitle());
        verify(preparedStatement).setString(3, song.getArtist());
        verify(preparedStatement).setString(4, song.getAlbum());
        verify(preparedStatement).setInt(5, song.getDuration());
        verify(preparedStatement).setString(6, song.getGenre());
        verify(preparedStatement).setInt(7, song.getPlayCount());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testRead() throws SQLException {
        String sql = "SELECT * FROM songs WHERE id = ?";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("title")).thenReturn("Title");
        when(resultSet.getString("artist")).thenReturn("Artist");
        when(resultSet.getString("album")).thenReturn("Album");
        when(resultSet.getInt("duration")).thenReturn(300);
        when(resultSet.getString("genre")).thenReturn("Genre");

        Song song = songDAO.read(1);

        assertNotNull(song);
        assertEquals(1, song.getId());
        assertEquals("Title", song.getTitle());
        assertEquals("Artist", song.getArtist());
        assertEquals("Album", song.getAlbum());
        assertEquals(300, song.getDuration());
        assertEquals("Genre", song.getGenre());
    }

    @Test
    void testReadAll() throws SQLException {
        String sql = "SELECT * FROM songs";
        Statement statement = mock(Statement.class);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(sql)).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getInt("id")).thenReturn(1, 2);
        when(resultSet.getString("title")).thenReturn("Title1", "Title2");
        when(resultSet.getString("artist")).thenReturn("Artist1", "Artist2");
        when(resultSet.getString("album")).thenReturn("Album1", "Album2");
        when(resultSet.getInt("duration")).thenReturn(300, 320);
        when(resultSet.getString("genre")).thenReturn("Genre1", "Genre2");

        List<Song> songs = songDAO.readAll();

        assertEquals(2, songs.size());

        Song song1 = songs.get(0);
        assertEquals(1, song1.getId());
        assertEquals("Title1", song1.getTitle());
        assertEquals("Artist1", song1.getArtist());
        assertEquals("Album1", song1.getAlbum());
        assertEquals(300, song1.getDuration());
        assertEquals("Genre1", song1.getGenre());

        Song song2 = songs.get(1);
        assertEquals(2, song2.getId());
        assertEquals("Title2", song2.getTitle());
        assertEquals("Artist2", song2.getArtist());
        assertEquals("Album2", song2.getAlbum());
        assertEquals(320, song2.getDuration());
        assertEquals("Genre2", song2.getGenre());
    }

    @Test
    void testUpdate() throws SQLException {
        String sql = "UPDATE songs SET title = ?, artist = ?, album = ?, duration = ?, genre = ?, playCount = ? WHERE id = ?";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);

        Song song = new Song(1, "Title", "Artist", "Album", 300, "Genre");
        song.incrementPlayCount();
        songDAO.update(song);

        verify(preparedStatement).setString(1, song.getTitle());
        verify(preparedStatement).setString(2, song.getArtist());
        verify(preparedStatement).setString(3, song.getAlbum());
        verify(preparedStatement).setInt(4, song.getDuration());
        verify(preparedStatement).setString(5, song.getGenre());
        verify(preparedStatement).setInt(6, song.getPlayCount());
        verify(preparedStatement).setInt(7, song.getId());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testDelete() throws SQLException {
        String sql = "DELETE FROM songs WHERE id = ?";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);

        songDAO.delete(1);

        verify(preparedStatement).setInt(1, 1);
        verify(preparedStatement).executeUpdate();
    }


}

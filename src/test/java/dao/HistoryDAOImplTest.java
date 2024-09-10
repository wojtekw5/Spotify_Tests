package dao;

import org.example.History;
import org.example.Song;
import org.example.User;
import org.example.dao.HistoryDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HistoryDAOImplTest {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private Statement statement;
    private HistoryDAOImpl historyDAO;
    private History history;
    private User user;
    private Song song;

    @BeforeEach
    void setUp() throws SQLException {
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
        statement = mock(Statement.class);
        historyDAO = spy(new HistoryDAOImpl(connection));

        user = new User(1, "Test User", "test@example.com", "password");
        song = new Song(1, "Test Song", "Test Artist", "Test Album", 300, "Test Genre");
        history = new History(user);
        history.getPlayedSongs().add(song);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(connection.createStatement()).thenReturn(statement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
    }

    @Test
    void testCreate() throws SQLException {
        historyDAO.create(history);

        verify(preparedStatement).setInt(1, history.getUser().getId());
        verify(preparedStatement).setInt(2, song.getId());
        verify(preparedStatement, times(history.getPlayedSongs().size())).executeUpdate();
    }

    @Test
    void testRead() throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("song_id")).thenReturn(song.getId());

        doReturn(song).when(historyDAO).getSongById(song.getId());

        History fetchedHistory = historyDAO.read(user);

        assertNotNull(fetchedHistory);
        assertEquals(1, fetchedHistory.getPlayedSongs().size());
        assertEquals(song.getId(), fetchedHistory.getPlayedSongs().get(0).getId());
    }

    @Test
    void testUpdate() throws SQLException {
        doNothing().when(historyDAO).delete(user);
        doNothing().when(historyDAO).create(history);

        historyDAO.update(history);

        verify(historyDAO).delete(user);
        verify(historyDAO).create(history);
    }

    @Test
    void testDelete() throws SQLException {
        historyDAO.delete(user);

        verify(preparedStatement).setInt(1, user.getId());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testReadAll() throws SQLException {
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("user_id")).thenReturn(user.getId());

        doReturn(user).when(historyDAO).getUserById(user.getId());
        doReturn(history).when(historyDAO).read(user);

        List<History> histories = historyDAO.readAll();

        assertNotNull(histories);
        assertEquals(1, histories.size());
        assertEquals(user.getId(), histories.get(0).getUser().getId());
    }

    @Test
    void testGetSongById() throws SQLException {
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(song.getId());
        when(resultSet.getString("title")).thenReturn(song.getTitle());
        when(resultSet.getString("artist")).thenReturn(song.getArtist());
        when(resultSet.getString("album")).thenReturn(song.getAlbum());
        when(resultSet.getInt("duration")).thenReturn(song.getDuration());
        when(resultSet.getString("genre")).thenReturn(song.getGenre());
        when(resultSet.getInt("play_count")).thenReturn(song.getPlayCount());

        Song fetchedSong = historyDAO.getSongById(song.getId());

        assertNotNull(fetchedSong);
        assertEquals(song.getId(), fetchedSong.getId());
        assertEquals(song.getTitle(), fetchedSong.getTitle());
        assertEquals(song.getArtist(), fetchedSong.getArtist());
        assertEquals(song.getAlbum(), fetchedSong.getAlbum());
        assertEquals(song.getDuration(), fetchedSong.getDuration());
        assertEquals(song.getGenre(), fetchedSong.getGenre());
        assertEquals(song.getPlayCount(), fetchedSong.getPlayCount());
    }

    @Test
    void testGetUserById() throws SQLException {
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(user.getId());
        when(resultSet.getString("username")).thenReturn(user.getName());
        when(resultSet.getString("email")).thenReturn(user.getEmail());
        when(resultSet.getString("password")).thenReturn(user.getPassword());

        User fetchedUser = historyDAO.getUserById(user.getId());

        assertNotNull(fetchedUser);
        assertEquals(user.getId(), fetchedUser.getId());
        assertEquals(user.getName(), fetchedUser.getName());
        assertEquals(user.getEmail(), fetchedUser.getEmail());
        assertEquals(user.getPassword(), fetchedUser.getPassword());
    }

    @Test
    void testCreateSQLException() throws SQLException {
        doThrow(SQLException.class).when(preparedStatement).executeUpdate();
        assertThrows(SQLException.class, () -> historyDAO.create(history));
    }

    @Test
    void testReadSQLException() throws SQLException {
        doThrow(SQLException.class).when(preparedStatement).executeQuery();
        assertThrows(SQLException.class, () -> historyDAO.read(user));
    }

    @Test
    void testUpdateSQLException() throws SQLException {
        doThrow(SQLException.class).when(preparedStatement).executeUpdate();
        assertThrows(SQLException.class, () -> historyDAO.update(history));
    }

    @Test
    void testDeleteSQLException() throws SQLException {
        doThrow(SQLException.class).when(preparedStatement).executeUpdate();
        assertThrows(SQLException.class, () -> historyDAO.delete(user));
    }
}

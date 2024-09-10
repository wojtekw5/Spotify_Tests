package dao;

import org.example.User;
import org.example.Song;
import org.example.Playlist;
import org.example.dao.UserDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDAOImplTest {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private UserDAOImpl userDAO;
    private User user;
    private Song song;
    private Playlist playlist;

    @BeforeEach
    void setUp() throws SQLException {
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);

        userDAO = new UserDAOImpl(connection);

        user = new User(1, "Jan Nowak", "jan@example.com", "password123");
        song = new Song(1, "Test Song", "Test Artist", "Test Album", 300, "Test Genre");
        playlist = new Playlist(1, "My Playlist", user);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
    }

    @Test
    void testCreate() throws SQLException {
        userDAO.create(user);

        verify(preparedStatement).setInt(1, user.getId());
        verify(preparedStatement).setString(2, user.getName());
        verify(preparedStatement).setString(3, user.getEmail());
        verify(preparedStatement).setString(4, user.getPassword());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testRead() throws SQLException {
        when(resultSet.getInt("id")).thenReturn(user.getId());
        when(resultSet.getString("name")).thenReturn(user.getName());
        when(resultSet.getString("email")).thenReturn(user.getEmail());
        when(resultSet.getString("password")).thenReturn(user.getPassword());

        User result = userDAO.read(1);

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getPassword(), result.getPassword());
    }

    @Test
    void testUpdate() throws SQLException {
        userDAO.update(user);

        verify(preparedStatement).setString(1, user.getName());
        verify(preparedStatement).setString(2, user.getEmail());
        verify(preparedStatement).setString(3, user.getPassword());
        verify(preparedStatement).setInt(4, user.getId());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testDelete() throws SQLException {
        userDAO.delete(user.getId());

        verify(preparedStatement).setInt(1, user.getId());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testReadAll() throws SQLException {
        String sql = "SELECT * FROM users";
        Statement statement = mock(Statement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(sql)).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("id")).thenReturn(user.getId());
        when(resultSet.getString("name")).thenReturn(user.getName());
        when(resultSet.getString("email")).thenReturn(user.getEmail());
        when(resultSet.getString("password")).thenReturn(user.getPassword());

        List<User> users = userDAO.readAll();

        assertNotNull(users);
        assertEquals(1, users.size());

        User result = users.get(0);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getPassword(), result.getPassword());
    }

    @Test
    void testAddFavoriteSong() throws SQLException {
        userDAO.addFavoriteSong(user.getId(), song);

        verify(preparedStatement).setInt(1, user.getId());
        verify(preparedStatement).setInt(2, song.getId());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testAddPlaylist() throws SQLException {
        userDAO.addPlaylist(user.getId(), playlist);

        verify(preparedStatement).setInt(1, playlist.getId());
        verify(preparedStatement).setString(2, playlist.getName());
        verify(preparedStatement).setInt(3, user.getId());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testAddPlayback() throws SQLException {
        userDAO.addPlayback(user.getId(), song);

        verify(preparedStatement).setInt(1, user.getId());
        verify(preparedStatement).setInt(2, song.getId());
        verify(preparedStatement).executeUpdate();
    }
}

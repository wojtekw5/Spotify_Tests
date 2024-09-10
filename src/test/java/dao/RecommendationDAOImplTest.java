package dao;

import org.example.Recommendation;
import org.example.Song;
import org.example.User;
import org.example.dao.RecommendationDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecommendationDAOImplTest {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private Statement statement;
    private RecommendationDAOImpl recommendationDAO;
    private Recommendation recommendation;
    private User user;
    private Song song;

    @BeforeEach
    void setUp() throws SQLException {
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
        statement = mock(Statement.class);
        recommendationDAO = spy(new RecommendationDAOImpl(connection));

        user = new User(1, "Test User", "test@example.com", "password");
        song = new Song(1, "Test Song", "Test Artist", "Test Album", 300, "Test Genre");
        recommendation = new Recommendation(user);
        recommendation.getRecommendedSongs().add(song);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(connection.createStatement()).thenReturn(statement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
    }

    @Test
    void testCreate() throws SQLException {
        recommendationDAO.create(recommendation);

        verify(preparedStatement).setInt(1, recommendation.getUser().getId());
        verify(preparedStatement).setInt(2, song.getId());
        verify(preparedStatement).addBatch();
        verify(preparedStatement).executeBatch();
    }

    @Test
    void testRead() throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("song_id")).thenReturn(song.getId());

        doReturn(song).when(recommendationDAO).getSongById(song.getId());

        Recommendation fetchedRecommendation = recommendationDAO.read(user);

        assertNotNull(fetchedRecommendation);
        assertEquals(1, fetchedRecommendation.getRecommendedSongs().size());
        assertEquals(song.getId(), fetchedRecommendation.getRecommendedSongs().get(0).getId());
    }

    @Test
    void testUpdate() throws SQLException {
        doNothing().when(recommendationDAO).delete(user);
        doNothing().when(recommendationDAO).create(recommendation);

        recommendationDAO.update(recommendation);

        verify(recommendationDAO).delete(user);
        verify(recommendationDAO).create(recommendation);
    }

    @Test
    void testDelete() throws SQLException {
        recommendationDAO.delete(user);

        verify(preparedStatement).setInt(1, user.getId());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testReadAll() throws SQLException {
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("user_id")).thenReturn(user.getId());

        doReturn(user).when(recommendationDAO).getUserById(user.getId());
        doReturn(recommendation).when(recommendationDAO).read(user);

        List<Recommendation> recommendations = recommendationDAO.readAll();

        assertNotNull(recommendations);
        assertEquals(1, recommendations.size());
        assertEquals(user.getId(), recommendations.get(0).getUser().getId());
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

        Song fetchedSong = recommendationDAO.getSongById(song.getId());

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
        when(resultSet.getString("name")).thenReturn(user.getName());

        User fetchedUser = recommendationDAO.getUserById(user.getId());

        assertNotNull(fetchedUser);
        assertEquals(user.getId(), fetchedUser.getId());
        assertEquals(user.getName(), fetchedUser.getName());
    }

    @Test
    void testCreateSQLException() throws SQLException {
        doThrow(SQLException.class).when(preparedStatement).executeBatch();
        assertThrows(SQLException.class, () -> recommendationDAO.create(recommendation));
    }

    @Test
    void testReadSQLException() throws SQLException {
        doThrow(SQLException.class).when(preparedStatement).executeQuery();
        assertThrows(SQLException.class, () -> recommendationDAO.read(user));
    }

    @Test
    void testUpdateSQLException() throws SQLException {
        doThrow(SQLException.class).when(preparedStatement).executeUpdate();
        assertThrows(SQLException.class, () -> recommendationDAO.update(recommendation));
    }

    @Test
    void testDeleteSQLException() throws SQLException {
        doThrow(SQLException.class).when(preparedStatement).executeUpdate();
        assertThrows(SQLException.class, () -> recommendationDAO.delete(user));
    }
}

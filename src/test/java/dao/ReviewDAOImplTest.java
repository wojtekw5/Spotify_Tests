package dao;

import org.example.Review;
import org.example.Song;
import org.example.User;
import org.example.dao.ReviewDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewDAOImplTest {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private Statement statement;
    private ReviewDAOImpl reviewDAO;
    private Review review;
    private User user;
    private Song song;

    @BeforeEach
    void setUp() throws SQLException {
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
        statement = mock(Statement.class);
        reviewDAO = spy(new ReviewDAOImpl(connection));

        user = new User(1, "Test User", "test@example.com", "password");
        song = new Song(1, "Test Song", "Test Artist", "Test Album", 300, "Test Genre");
        review = new Review(1, user, song, "Great song!", 5);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(connection.createStatement()).thenReturn(statement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
    }

    @Test
    void testCreate() throws SQLException {
        reviewDAO.create(review);

        verify(preparedStatement).setInt(1, review.getId());
        verify(preparedStatement).setInt(2, review.getUser().getId());
        verify(preparedStatement).setInt(3, review.getSong().getId());
        verify(preparedStatement).setString(4, review.getReviewText());
        verify(preparedStatement).setInt(5, review.getRating());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testRead() throws SQLException {
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(review.getId());
        when(resultSet.getInt("user_id")).thenReturn(review.getUser().getId());
        when(resultSet.getInt("song_id")).thenReturn(review.getSong().getId());
        when(resultSet.getString("review_text")).thenReturn(review.getReviewText());
        when(resultSet.getInt("rating")).thenReturn(review.getRating());

        doReturn(user).when(reviewDAO).getUserById(user.getId());
        doReturn(song).when(reviewDAO).getSongById(song.getId());

        Review fetchedReview = reviewDAO.read(1);

        assertNotNull(fetchedReview);
        assertEquals(review.getId(), fetchedReview.getId());
        assertEquals(review.getUser().getId(), fetchedReview.getUser().getId());
        assertEquals(review.getSong().getId(), fetchedReview.getSong().getId());
        assertEquals(review.getReviewText(), fetchedReview.getReviewText());
        assertEquals(review.getRating(), fetchedReview.getRating());
    }

    @Test
    void testReadAll() throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("id")).thenReturn(review.getId());
        when(resultSet.getInt("user_id")).thenReturn(review.getUser().getId());
        when(resultSet.getInt("song_id")).thenReturn(review.getSong().getId());
        when(resultSet.getString("review_text")).thenReturn(review.getReviewText());
        when(resultSet.getInt("rating")).thenReturn(review.getRating());

        doReturn(user).when(reviewDAO).getUserById(user.getId());
        doReturn(song).when(reviewDAO).getSongById(song.getId());

        List<Review> reviews = reviewDAO.readAll();

        assertNotNull(reviews);
        assertEquals(1, reviews.size());
        Review fetchedReview = reviews.get(0);
        assertEquals(review.getId(), fetchedReview.getId());
        assertEquals(review.getUser().getId(), fetchedReview.getUser().getId());
        assertEquals(review.getSong().getId(), fetchedReview.getSong().getId());
        assertEquals(review.getReviewText(), fetchedReview.getReviewText());
        assertEquals(review.getRating(), fetchedReview.getRating());
    }

    @Test
    void testUpdate() throws SQLException {
        reviewDAO.update(review);

        verify(preparedStatement).setString(1, review.getReviewText());
        verify(preparedStatement).setInt(2, review.getRating());
        verify(preparedStatement).setInt(3, review.getId());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testDelete() throws SQLException {
        reviewDAO.delete(review.getId());

        verify(preparedStatement).setInt(1, review.getId());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testGetUserById() throws SQLException {
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(user.getId());
        when(resultSet.getString("name")).thenReturn(user.getName());

        User fetchedUser = reviewDAO.getUserById(user.getId());

        assertNotNull(fetchedUser);
        assertEquals(user.getId(), fetchedUser.getId());
        assertEquals(user.getName(), fetchedUser.getName());
    }

    @Test
    void testGetUserByIdNotFound() throws SQLException {
        when(resultSet.next()).thenReturn(false);

        User fetchedUser = reviewDAO.getUserById(999);

        assertNull(fetchedUser, "Metoda powinna zwrócić null, jeśli użytkownik nie został znaleziony");
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

        Song fetchedSong = reviewDAO.getSongById(song.getId());

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
    void testGetSongByIdNotFound() throws SQLException {
        when(resultSet.next()).thenReturn(false);

        Song fetchedSong = reviewDAO.getSongById(999);

        assertNull(fetchedSong, "Metoda powinna zwrócić null, jeśli piosenka nie została znaleziona");
    }

    @Test
    void testCreateSQLException() throws SQLException {
        doThrow(SQLException.class).when(preparedStatement).executeUpdate();
        assertThrows(SQLException.class, () -> reviewDAO.create(review));
    }

    @Test
    void testReadSQLException() throws SQLException {
        doThrow(SQLException.class).when(preparedStatement).executeQuery();
        assertThrows(SQLException.class, () -> reviewDAO.read(1));
    }

    @Test
    void testUpdateSQLException() throws SQLException {
        doThrow(SQLException.class).when(preparedStatement).executeUpdate();
        assertThrows(SQLException.class, () -> reviewDAO.update(review));
    }

    @Test
    void testDeleteSQLException() throws SQLException {
        doThrow(SQLException.class).when(preparedStatement).executeUpdate();
        assertThrows(SQLException.class, () -> reviewDAO.delete(review.getId()));
    }
}

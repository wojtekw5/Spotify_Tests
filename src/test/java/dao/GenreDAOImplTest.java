package dao;

import org.example.Genre;
import org.example.dao.GenreDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GenreDAOImplTest {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private GenreDAOImpl genreDAO;

    @BeforeEach
    void setUp() throws SQLException {
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
        genreDAO = new GenreDAOImpl(connection);
    }

    @Test
    void testCreate() throws SQLException {
        String sql = "INSERT INTO genres (id, name) VALUES (?, ?)";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);

        Genre genre = new Genre(1, "Rock");
        genreDAO.create(genre);

        verify(preparedStatement).setInt(1, genre.getId());
        verify(preparedStatement).setString(2, genre.getName());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testRead() throws SQLException {
        String sql = "SELECT * FROM genres WHERE id = ?";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("name")).thenReturn("Rock");

        Genre genre = genreDAO.read(1);

        assertNotNull(genre);
        assertEquals(1, genre.getId());
        assertEquals("Rock", genre.getName());
    }

    @Test
    void testReadAll() throws SQLException {
        String sql = "SELECT * FROM genres";
        Statement statement = mock(Statement.class);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(sql)).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getInt("id")).thenReturn(1, 2);
        when(resultSet.getString("name")).thenReturn("Rock", "Pop");

        List<Genre> genres = genreDAO.readAll();

        assertEquals(2, genres.size());

        Genre genre1 = genres.get(0);
        assertEquals(1, genre1.getId());
        assertEquals("Rock", genre1.getName());

        Genre genre2 = genres.get(1);
        assertEquals(2, genre2.getId());
        assertEquals("Pop", genre2.getName());
    }

    @Test
    void testUpdate() throws SQLException {
        String sql = "UPDATE genres SET name = ? WHERE id = ?";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);

        Genre genre = new Genre(1, "Rock");
        genreDAO.update(genre);

        verify(preparedStatement).setString(1, genre.getName());
        verify(preparedStatement).setInt(2, genre.getId());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testDelete() throws SQLException {
        String sql = "DELETE FROM genres WHERE id = ?";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);

        genreDAO.delete(1);

        verify(preparedStatement).setInt(1, 1);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testCreateSQLException() throws SQLException {
        String sql = "INSERT INTO genres (id, name) VALUES (?, ?)";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        doThrow(new SQLException()).when(preparedStatement).executeUpdate();

        Genre genre = new Genre(1, "Rock");

        assertThrows(SQLException.class, () -> genreDAO.create(genre));
    }

    @Test
    void testReadSQLException() throws SQLException {
        String sql = "SELECT * FROM genres WHERE id = ?";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        doThrow(new SQLException()).when(preparedStatement).executeQuery();

        assertThrows(SQLException.class, () -> genreDAO.read(1));
    }

    @Test
    void testReadAllSQLException() throws SQLException {
        String sql = "SELECT * FROM genres";
        Statement statement = mock(Statement.class);
        when(connection.createStatement()).thenReturn(statement);
        doThrow(new SQLException()).when(statement).executeQuery(sql);

        assertThrows(SQLException.class, () -> genreDAO.readAll());
    }

    @Test
    void testUpdateSQLException() throws SQLException {
        String sql = "UPDATE genres SET name = ? WHERE id = ?";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        doThrow(new SQLException()).when(preparedStatement).executeUpdate();

        Genre genre = new Genre(1, "Rock");

        assertThrows(SQLException.class, () -> genreDAO.update(genre));
    }

    @Test
    void testDeleteSQLException() throws SQLException {
        String sql = "DELETE FROM genres WHERE id = ?";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        doThrow(new SQLException()).when(preparedStatement).executeUpdate();

        assertThrows(SQLException.class, () -> genreDAO.delete(1));
    }
}

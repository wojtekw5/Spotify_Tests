package dao;

import org.example.Artist;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.example.dao.ArtistDAO;
import org.example.dao.ArtistDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArtistDAOImplTest {
    private Connection connection;
    private ArtistDAO artistDAO;
    private Artist artist;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private Statement statement;

    @BeforeEach
    void setUp() throws Exception {
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
        statement = mock(Statement.class);

        artistDAO = new ArtistDAOImpl(connection);

        artist = new Artist(1, "Test Artist");

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(connection.createStatement()).thenReturn(statement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    @Test
    void testCreate() throws Exception {
        String sql = "INSERT INTO artists (id, name) VALUES (?, ?)";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);

        artistDAO.create(artist);

        verify(preparedStatement).setInt(1, artist.getId());
        verify(preparedStatement).setString(2, artist.getName());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testRead() throws Exception {
        String sql = "SELECT * FROM artists WHERE id = ?";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(artist.getId());
        when(resultSet.getString("name")).thenReturn(artist.getName());

        Artist result = artistDAO.read(1);

        assertNotNull(result);
        assertEquals(artist.getId(), result.getId());
        assertEquals(artist.getName(), result.getName());
    }

    @Test
    void testReadNotFound() throws Exception {
        String sql = "SELECT * FROM artists WHERE id = ?";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);

        when(resultSet.next()).thenReturn(false);

        Artist result = artistDAO.read(1);

        assertNull(result);
    }

    @Test
    void testUpdate() throws Exception {
        String sql = "UPDATE artists SET name = ? WHERE id = ?";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);

        artistDAO.update(artist);

        verify(preparedStatement).setString(1, artist.getName());
        verify(preparedStatement).setInt(2, artist.getId());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testDelete() throws Exception {
        String sql = "DELETE FROM artists WHERE id = ?";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);

        artistDAO.delete(1);

        verify(preparedStatement).setInt(1, 1);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testGetAllArtists() throws Exception {
        String sql = "SELECT * FROM artists";
        when(statement.executeQuery(sql)).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("id")).thenReturn(artist.getId());
        when(resultSet.getString("name")).thenReturn(artist.getName());

        List<Artist> artists = artistDAO.getAllArtists();

        assertNotNull(artists);
        assertEquals(1, artists.size());
        Artist result = artists.get(0);
        assertEquals(artist.getId(), result.getId());
        assertEquals(artist.getName(), result.getName());
    }
}

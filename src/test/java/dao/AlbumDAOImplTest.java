package dao;

import org.example.Album;
import org.example.dao.AlbumDAOImpl;
import org.example.Artist;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AlbumDAOImplTest {
    private Connection connection;
    private AlbumDAOImpl albumDAO;
    private Artist artist;
    private Album album;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    @BeforeEach
    void setUp() throws Exception {
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
        albumDAO = Mockito.spy(new AlbumDAOImpl(connection));

        artist = new Artist(1, "Test Artist");
        album = new Album(1, "Test Album", artist, 2022);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    @Test
    void testCreate() throws Exception {
        String sql = "INSERT INTO albums (id, title, artist_id, release_year) VALUES (?, ?, ?, ?)";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);

        albumDAO.create(album);

        verify(preparedStatement).setInt(1, album.getId());
        verify(preparedStatement).setString(2, album.getTitle());
        verify(preparedStatement).setInt(3, album.getArtist().getId());
        verify(preparedStatement).setInt(4, album.getReleaseYear());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testRead() throws Exception {
        String sql = "SELECT * FROM albums WHERE id = ?";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(album.getId());
        when(resultSet.getString("title")).thenReturn(album.getTitle());
        when(resultSet.getInt("artist_id")).thenReturn(artist.getId());
        when(resultSet.getInt("release_year")).thenReturn(album.getReleaseYear());

        doReturn(artist).when(albumDAO).getArtistById(artist.getId());

        Album result = albumDAO.read(1);

        assertNotNull(result);
        assertEquals(album.getId(), result.getId());
        assertEquals(album.getTitle(), result.getTitle());
        assertEquals(album.getArtist().getId(), result.getArtist().getId());
        assertEquals(album.getReleaseYear(), result.getReleaseYear());
    }

    @Test
    void testUpdate() throws Exception {
        String sql = "UPDATE albums SET title = ?, artist_id = ?, release_year = ? WHERE id = ?";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);

        albumDAO.update(album);

        verify(preparedStatement).setString(1, album.getTitle());
        verify(preparedStatement).setInt(2, album.getArtist().getId());
        verify(preparedStatement).setInt(3, album.getReleaseYear());
        verify(preparedStatement).setInt(4, album.getId());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testDelete() throws Exception {
        String sql = "DELETE FROM albums WHERE id = ?";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);

        albumDAO.delete(1);

        verify(preparedStatement).setInt(1, 1);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testGetAllAlbums() throws Exception {
        String sql = "SELECT * FROM albums";
        Statement statement = mock(Statement.class);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(sql)).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("id")).thenReturn(album.getId());
        when(resultSet.getString("title")).thenReturn(album.getTitle());
        when(resultSet.getInt("artist_id")).thenReturn(artist.getId());
        when(resultSet.getInt("release_year")).thenReturn(album.getReleaseYear());

        doReturn(artist).when(albumDAO).getArtistById(artist.getId());

        List<Album> albums = albumDAO.getAllAlbums();

        assertNotNull(albums);
        assertEquals(1, albums.size());
        Album result = albums.get(0);
        assertEquals(album.getId(), result.getId());
        assertEquals(album.getTitle(), result.getTitle());
        assertEquals(album.getArtist().getId(), result.getArtist().getId());
        assertEquals(album.getReleaseYear(), result.getReleaseYear());
    }

    @Test
    void testGetArtistByIdFound() throws Exception {
        String sql = "SELECT * FROM artists WHERE id = ?";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(artist.getId());
        when(resultSet.getString("name")).thenReturn(artist.getName());

        Artist result = albumDAO.getArtistById(1);

        assertNotNull(result);
        assertEquals(artist.getId(), result.getId());
        assertEquals(artist.getName(), result.getName());
    }

    @Test
    void testGetArtistByIdNotFound() throws Exception {
        String sql = "SELECT * FROM artists WHERE id = ?";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);

        when(resultSet.next()).thenReturn(false);

        Artist result = albumDAO.getArtistById(9);

        assertNull(result, "Metoda powinna zwrócić null, jeśli artysta nie został znaleziony");
    }
}


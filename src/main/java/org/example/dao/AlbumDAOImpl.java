package org.example.dao;

import org.example.Album;
import org.example.Artist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlbumDAOImpl implements AlbumDAO {
    private final Connection connection;

    public AlbumDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Album album) {
        String sql = "INSERT INTO albums (id, title, artist_id, release_year) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, album.getId());
            statement.setString(2, album.getTitle());
            statement.setInt(3, album.getArtist().getId());
            statement.setInt(4, album.getReleaseYear());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Album read(int id) {
        String sql = "SELECT * FROM albums WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString("title");
                int artistId = resultSet.getInt("artist_id");
                int releaseYear = resultSet.getInt("release_year");

                Artist artist = getArtistById(artistId);
                Album album = new Album(id, title, artist, releaseYear);
                return album;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Album album) {
        String sql = "UPDATE albums SET title = ?, artist_id = ?, release_year = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, album.getTitle());
            statement.setInt(2, album.getArtist().getId());
            statement.setInt(3, album.getReleaseYear());
            statement.setInt(4, album.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM albums WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Album> getAllAlbums() {
        List<Album> albums = new ArrayList<>();
        String sql = "SELECT * FROM albums";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                int artistId = resultSet.getInt("artist_id");
                int releaseYear = resultSet.getInt("release_year");

                Artist artist = getArtistById(artistId);
                Album album = new Album(id, title, artist, releaseYear);
                albums.add(album);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return albums;
    }

    public Artist getArtistById(int id) throws SQLException {
        String sql = "SELECT * FROM artists WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Artist(
                            resultSet.getInt("id"),
                            resultSet.getString("name")
                    );
                }
            }
        }
        return null;
    }
}

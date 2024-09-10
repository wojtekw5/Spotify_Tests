package steps;

import org.example.User;
import org.example.Song;
import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class MusicRecommendationsSteps {

    private User user;
    private List<Song> recommendedSongs;

    @Given("A user named {string} with id {int}")
    public void a_user_named_with_id(String name, int id) {
        user = new User(id, name, name.toLowerCase() + "@example.com", "password123");
    }

    @Given("{string} has favorite songs in genre {string}")
    public void user_has_favorite_songs_in_genre(String userName, String genre) {
        for (int i = 0; i < 5; i++) {
            user.addFavoriteSong(new Song(i + 1, genre + " Song " + (i + 1), "Artist", "Album", 200, genre));
        }
    }

    @When("{string} requests genre-specific recommendations")
    public void user_requests_genre_specific_recommendations(String userName) {
        recommendedSongs = getGenreSpecificRecommendations(user);
    }

    @Then("{string} receives song recommendations in genre {string}")
    public void user_receives_song_recommendations_in_genre(String userName, String genre) {
        assertNotNull(recommendedSongs);
        assertFalse(recommendedSongs.isEmpty());
        assertTrue(recommendedSongs.stream().allMatch(song -> song.getGenre().equals(genre)));
    }

    private List<Song> getGenreSpecificRecommendations(User user) {
        String favoriteGenre = user.getFavoriteSongs().get(0).getGenre(); //pobieranie gatunku z pierwszego pobranego utworu
        List<Song> recommendations = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            recommendations.add(new Song(i + 1, favoriteGenre + " Recommended Song " + (i + 1), "Artist", "Album", 200, favoriteGenre));
        }
        return recommendations;
    }
}

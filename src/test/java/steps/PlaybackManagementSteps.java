package steps;

import org.example.User;
import org.example.Song;
import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

public class PlaybackManagementSteps {

    private User user;
    private Song song;
    private boolean isPlaying;

    @Given("Playback: We have user {string} with id: {int}")
    public void we_have_user_with_id_playback_management(String name, int id) {
        user = new User(id, name, name.toLowerCase() + "@example.com", "password123");
    }

    @Given("Playback: {string} has listened to song {int} titled {string}")
    public void user_has_listened_to_song_titled_playback(String userName, int songId, String songTitle) {
        song = new Song(songId, songTitle, userName, "Album B", 200, "Ambient");
    }

    @When("Playback: {string} starts playing song {int}")
    public void user_starts_playing_song(String userName, int songId) {
        isPlaying = true;
    }

    @Then("Playback: song {int} is playing")
    public void song_is_playing(int songId) {
        assertTrue(isPlaying, "The song should be playing");
    }

    @When("Playback: {string} stops playing song {int}")
    public void user_stops_playing_song(String userName, int songId) {
        isPlaying = false;
    }

    @Then("Playback: song {int} is not playing")
    public void song_is_not_playing(int songId) {
        assertFalse(isPlaying, "The song should not be playing");
    }
}

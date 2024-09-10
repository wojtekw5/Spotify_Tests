package steps;

import org.example.Song;
import org.example.User;
import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

public class PlaybackHistorySteps {

    private User user;
    private Song song;

    @Given("We have playback user {string} with id {int}")
    public void we_have_playback_user_with_id(String name, int id) {
        user = new User(id, name, name.toLowerCase() + "@example.com", "password123");
    }

    @Given("{string} has playback history for song with id {int} titled {string}")
    public void user_has_playback_history_for_song_with_id_titled(String userName, int songId, String songTitle) {
        song = new Song(songId, songTitle, userName, "Album A", 210, "Pop");
        user.addPlayback(song);
    }

    @Then("{string} should see song with id {int} in playback history")
    public void user_should_see_song_with_id_in_playback_history(String userName, int songId) {
        assertTrue(user.getPlaybackHistory().stream().anyMatch(s -> s.getId() == songId));
    }

    @When("{string} plays song with id {int} titled {string}")
    public void user_plays_song_with_id_titled(String userName, int songId, String songTitle) {
        song = new Song(songId, songTitle, userName, "Album A", 210, "Pop");
        user.addPlayback(song);
    }
}

package steps;

        import org.example.Subscription;
        import io.cucumber.java.en.*;

        import java.util.Date;

        import static org.junit.jupiter.api.Assertions.*;

public class OverTheDailyLimitSteps {

    private Subscription subscription;
    private Exception exception;

    @Given("\"John\" has a subscription with type {string} and status {string}")
    public void john_has_a_subscription_with_type_and_status(String type, String status) {
        subscription = new Subscription(1, type, new Date(), new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000), status);
    }

    @Given("John has played {int} times today")
    public void john_has_played_times_today(int plays) {
        for (int i = 0; i < plays; i++) {
            subscription.play();
        }
    }

    @When("John tries to play again")
    public void john_tries_to_play_again() {
        try {
            subscription.play();
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("John should see an error {string}")
    public void john_should_see_an_error(String expectedMessage) {
        assertNotNull(exception);
        assertEquals(expectedMessage, exception.getMessage());
    }
}

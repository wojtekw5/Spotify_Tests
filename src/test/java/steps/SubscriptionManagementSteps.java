package steps;

import org.example.Subscription;
import org.example.User;
import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SubscriptionManagementSteps {

    private User user;
    private Subscription subscription;

    @Given("We have user {string} with id {int}")
    public void we_have_user_with_id_subscription_management(String name, int id) {
        user = new User(id, name, name.toLowerCase() + "@example.com", "password123");
    }

    @Given("{string} has a subscription with type {string}, start date {string}, end date {string}, and status {string}")
    public void user_has_a_subscription_with_details(String userName, String subscriptionType, String startDate, String endDate, String status) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date start = sdf.parse(startDate);
        Date end = sdf.parse(endDate);
        subscription = new Subscription(1, subscriptionType, start, end, status);
        user.setSubscription(subscription);
    }

    @When("{string} renews subscription to end date {string}")
    public void user_renews_subscription(String userName, String newEndDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date newEnd = sdf.parse(newEndDate);
        subscription.renewSubscription(newEnd);
    }

    @Then("{string} has a subscription with end date {string} and status {string}")
    public void user_has_a_subscription_with_end_date_and_status(String userName, String expectedEndDate, String expectedStatus) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date expectedEnd = sdf.parse(expectedEndDate);
        assertEquals(expectedEnd, subscription.getEndDate());
        assertEquals(expectedStatus, subscription.getStatus());
    }
}

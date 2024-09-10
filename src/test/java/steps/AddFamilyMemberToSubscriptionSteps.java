package steps;

import org.example.Subscription;
import io.cucumber.java.en.*;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class AddFamilyMemberToSubscriptionSteps {

    private Subscription subscription;

    @Given("\"Tom\" has a subscription with type {string} and status {string}")
    public void tom_has_a_subscription_with_type_and_status(String type, String status) {
        subscription = new Subscription(1, type, new Date(), new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000), status);
    }

    @When("\"Tom\" adds a family member with ID {int} and name {string}")
    public void tom_adds_a_family_member_with_id_and_name(int memberId, String memberName) {
        subscription.addFamilyMember(memberId, memberName);
    }

    @Then("the family plan should contain a member with ID {int} and name {string}")
    public void the_family_plan_should_contain_a_member_with_id_and_name(int memberId, String memberName) {
        assertTrue(subscription.getFamilyMembers().containsKey(memberId));
        assertEquals(memberName, subscription.getFamilyMembers().get(memberId));
    }
}

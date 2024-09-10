import org.example.Subscription;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

public class SubscriptionTest {

    private Subscription subscription;
    private Date startDate;
    private Date endDate;

    @BeforeEach
    public void setUp() {
        startDate = new Date(); // bieżąca data
        endDate = new Date(startDate.getTime() + 30L * 24 * 60 * 60 * 1000); // 30 dni później
        subscription = new Subscription(1, "Premium", startDate, endDate, "Active");
    }

    @Test
    public void testGetId() {
        assertEquals(1, subscription.getId());
    }

    @Test
    public void testGetType() {
        assertEquals("Premium", subscription.getType());
    }

    @Test
    public void testGetStartDate() {
        assertEquals(startDate, subscription.getStartDate());
    }

    @Test
    public void testGetEndDate() {
        assertEquals(endDate, subscription.getEndDate());
    }

    @Test
    public void testGetStatus() {
        assertEquals("Active", subscription.getStatus());
    }

    @Test
    public void testRenewSubscription() {
        Date newEndDate = new Date(endDate.getTime() + 30L * 24 * 60 * 60 * 1000); // 30 dni później
        subscription.renewSubscription(newEndDate);
        assertEquals(newEndDate, subscription.getEndDate());
        assertEquals("Active", subscription.getStatus());
    }

    @Test
    public void testRenewSubscriptionToInactive() {
        subscription = new Subscription(1, "Premium", startDate, endDate, "Inactive");
        Date newEndDate = new Date(endDate.getTime() + 30L * 24 * 60 * 60 * 1000); // 30 dni później
        subscription.renewSubscription(newEndDate);
        assertEquals(newEndDate, subscription.getEndDate());
        assertEquals("Active", subscription.getStatus());
    }

    @Test
    public void testCancelSubscription() {
        subscription.cancelSubscription();
        assertEquals("Cancelled", subscription.getStatus());
        assertTrue(subscription.getEndDate().getTime() <= new Date().getTime());
    }

    @Test
    public void testUpdateStatusExpired() {
        Date currentDate = new Date(endDate.getTime() + 1); // Jeden dzień po zakończeniu
        subscription.updateStatus(currentDate);
        assertEquals("Expired", subscription.getStatus());
    }

    @Test
    public void testUpdateStatusBeforeExpiration() {
        Date currentDate = new Date(endDate.getTime() - 1); // Jeden dzień przed zakończeniem
        subscription.updateStatus(currentDate);
        assertEquals("Active", subscription.getStatus());
    }

    @Test
    public void testPlayCountForFreePlan() {
        subscription = new Subscription(2, "Free", startDate, endDate, "Active");
        for (int i = 0; i < 50; i++) {
            subscription.play();
        }
        assertEquals(50, subscription.getPlayCount());

        // następna próba odtworzenia powinna rzucić wyjątek
        Exception exception = assertThrows(RuntimeException.class, () -> {
            subscription.play();
        });
        assertEquals("Play limit reached for Free plan. Upgrade to Premium or Family for unlimited access.", exception.getMessage());
    }

    @Test
    public void testUnlimitedPlayCountForPremiumPlan() {
        subscription = new Subscription(3, "Premium", startDate, endDate, "Active");
        for (int i = 0; i < 100; i++) {
            subscription.play();
        }
        assertEquals(100, subscription.getPlayCount()); // Sprawdzamy, że można odtwarzać bez limitu
    }

    @Test
    public void testAddAndRemoveFamilyMembers() {
        subscription = new Subscription(4, "Family", startDate, endDate, "Active");
        subscription.addFamilyMember(101, "Jan Kowalski");
        subscription.addFamilyMember(102, "Jan Nowak");

        assertEquals(2, subscription.getFamilyMembers().size());
        assertTrue(subscription.getFamilyMembers().containsKey(101));
        assertTrue(subscription.getFamilyMembers().containsKey(102));
        assertEquals("Jan Kowalski", subscription.getFamilyMembers().get(101));
        assertEquals("Jan Nowak", subscription.getFamilyMembers().get(102));

        subscription.removeFamilyMember(102);
        assertEquals(1, subscription.getFamilyMembers().size());
        assertTrue(subscription.getFamilyMembers().containsKey(101));
        assertFalse(subscription.getFamilyMembers().containsKey(102));
    }

    @Test
    public void testCannotAddFamilyMemberToNonFamilyPlan() {
        subscription = new Subscription(5, "Premium", startDate, endDate, "Active");
        Exception exception = assertThrows(UnsupportedOperationException.class, () -> {
            subscription.addFamilyMember(101, "Jan Nowak");
        });
        assertEquals("Cannot add family members to non-family plan", exception.getMessage());
    }

    @Test
    public void testChangeStatus() {
        subscription.changeStatus("Suspended");
        assertEquals("Suspended", subscription.getStatus());
    }

    @Test
    public void testResetPlayCount() {
        subscription = new Subscription(6, "Free", startDate, endDate, "Active");
        for (int i = 0; i < 10; i++) {
            subscription.play();
        }
        assertEquals(10, subscription.getPlayCount());

        subscription.resetPlayCount();
        assertEquals(0, subscription.getPlayCount());
    }
}

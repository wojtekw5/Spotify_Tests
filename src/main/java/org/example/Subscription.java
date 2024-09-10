package org.example;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Subscription {
    private int id;
    private String type; // "Premium", "Free", "Family"
    private Date startDate;
    private Date endDate;
    private String status;
    private int playCount;
    private static final int FREE_PLAN_LIMIT = 50;
    private Map<Integer, String> familyMembers;

    public Subscription(int id, String type, Date startDate, Date endDate, String status) {
        this.id = id;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.playCount = 0;
        this.familyMembers = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getStatus() {
        return status;
    }

    public boolean canPlay() {
        if (type.equals("Free")) {
            return playCount < FREE_PLAN_LIMIT;
        }
        return true; // premium i family nielimitowany
    }

    public void play() {
        if (canPlay()) {
            playCount++;
        } else {
            throw new RuntimeException("Play limit reached for Free plan. Upgrade to Premium or Family for unlimited access.");
        }
    }

    public void resetPlayCount() {
        this.playCount = 0;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void renewSubscription(Date newEndDate) {
        this.endDate = newEndDate;
        this.status = "Active";
    }

    public void changeStatus(String newStatus) {
        this.status = newStatus;
    }

    public void cancelSubscription() {
        this.status = "Cancelled";
        this.endDate = new Date(); // Ustawienie daty końca na aktualną
    }

    public void updateStatus(Date currentDate) {
        if (currentDate.after(endDate)) {
            this.status = "Expired";
        }
    }

    public void addFamilyMember(int memberId, String memberName) {
        if (type.equals("Family")) {
            familyMembers.put(memberId, memberName);
        } else {
            throw new UnsupportedOperationException("Cannot add family members to non-family plan");
        }
    }

    public void removeFamilyMember(int memberId) {
        familyMembers.remove(memberId);
    }

    public Map<Integer, String> getFamilyMembers() {
        return familyMembers;
    }
}

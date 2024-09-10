Feature: Family Plan Management

  Scenario: Add a family member to a Family plan by ID
    Given "Tom" has a subscription with type "Family" and status "Active"
    When "Tom" adds a family member with ID 101 and name "Anna"
    Then the family plan should contain a member with ID 101 and name "Anna"

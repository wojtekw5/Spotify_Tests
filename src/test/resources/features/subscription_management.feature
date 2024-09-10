Feature: Subscription Management

  Scenario: Renewing a subscription
    Given We have user "Anna" with id 1
    And "Anna" has a subscription with type "Premium", start date "2023-01-01", end date "2023-12-31", and status "Active"
    When "Anna" renews subscription to end date "2024-12-31"
    Then "Anna" has a subscription with end date "2024-12-31" and status "Active"


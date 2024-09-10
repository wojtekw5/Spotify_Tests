Feature: Subscription Play Limits

  Scenario: John has reached his daily play limit
    Given "John" has a subscription with type "Free" and status "Active"
    And John has played 50 times today
    When John tries to play again
    Then John should see an error "Play limit reached for Free plan. Upgrade to Premium or Family for unlimited access."

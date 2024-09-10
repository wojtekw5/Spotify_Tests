Feature: Review Management

  Scenario: Writing a review for a song
    Given We have user "Anna" with id: 1
    And "Anna" has listened to song 201 titled "Summer Vibes"
    When "Anna" writes a review "Great song!" with rating 5 for song 201
    Then song 201 has a review by user 1 with text "Great song!" and rating 5

  Scenario: Editing a review for a song
    Given We have user "Anna" with id: 1
    And "Anna" has listened to song 202 titled "Chill night"
    And "Anna" writes a review "Boring track" with rating 2 for song 202
    When "Anna" edits her review for song 202 to "Amazing track" with rating 5
    Then song 202 has a review by user 1 with text "Amazing track" and rating 5

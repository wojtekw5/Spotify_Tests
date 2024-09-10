Feature: Playback History Management

  Scenario: Adding a song to playback history
    Given We have playback user "Anna" with id 1
    When "Anna" plays song with id 101 titled "Summer vibes"
    Then "Anna" should see song with id 101 in playback history

  Scenario: Checking playback history for a listened song
    Given We have playback user "Anna" with id 1
    And "Anna" has playback history for song with id 102 titled "Chill night"
    Then "Anna" should see song with id 102 in playback history

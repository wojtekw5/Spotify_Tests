Feature: Playback Management

  Scenario: Starting playback of a song
    Given Playback: We have user "Tom" with id: 2
    And Playback: "Tom" has listened to song 301 titled "Leaves Me"
    When Playback: "Tom" starts playing song 301
    Then Playback: song 301 is playing

  Scenario: Stopping playback of a song
    Given Playback: We have user "Tom" with id: 2
    And Playback: "Tom" has listened to song 301 titled "Leaves Me"
    And Playback: "Tom" starts playing song 301
    When Playback: "Tom" stops playing song 301
    Then Playback: song 301 is not playing

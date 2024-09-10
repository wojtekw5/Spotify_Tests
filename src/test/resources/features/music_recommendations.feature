Feature: Music Recommendations

  Scenario: User receives recommendations about genres
    Given A user named "Anna" with id 1
    Given "Anna" has favorite songs in genre "Rock"
    When "Anna" requests genre-specific recommendations
    Then "Anna" receives song recommendations in genre "Rock"

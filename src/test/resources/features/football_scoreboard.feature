Feature: Football World Cup Scoreboard

  Scenario: Start a game
    Given the scoreboard is empty
    When I start a game between "Mexico" and "Canada"
    Then the scoreboard should show "Mexico 0 - Canada 0"

  Scenario: Finish a game
    Given a game between "Mexico" and "Canada" has started
    When I finish the game between "Mexico" and "Canada"
    Then the scoreboard should be empty

  Scenario: Update a score
    Given a game between "Mexico" and "Canada" has started
    When I update the score to "2" for "Mexico" and "3" for "Canada"
    Then the scoreboard should show "Mexico 2 - Canada 3"

  Scenario: Get summary of games
    Given the following games have started:
      | HomeTeam   | AwayTeam   | HomeScore | AwayScore |
      | Mexico     | Canada     | 0         | 5         |
      | Spain      | Brazil     | 10        | 2         |
      | Germany    | France     | 2         | 2         |
      | Uruguay    | Italy      | 6         | 6         |
      | Argentina  | Australia  | 3         | 1         |
    When I get the summary
    Then the summary should be:
      | Match                     |
      | Uruguay 6 - Italy 6       |
      | Spain 10 - Brazil 2       |
      | Mexico 0 - Canada 5       |
      | Argentina 3 - Australia 1 |
      | Germany 2 - France 2      |
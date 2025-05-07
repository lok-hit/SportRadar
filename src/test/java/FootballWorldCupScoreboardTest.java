import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FootballWorldCupScoreboardTest {

    private FootballWorldCupScoreboard scoreboard;

    @BeforeEach
    void setUp() {
        scoreboard = new FootballWorldCupScoreboard();
    }

    @Test
    void testStartGame() {
        scoreboard.startGame("Mexico", "Canada");
        List<String> summary = scoreboard.getSummary();
        assertEquals(1, summary.size());
        assertEquals("Mexico 0 - Canada 0", summary.get(0));
    }

    @Test
    void testFinishGame() {
        scoreboard.startGame("Mexico", "Canada");
        scoreboard.finishGame("Mexico", "Canada");
        List<String> summary = scoreboard.getSummary();
        assertTrue(summary.isEmpty());
    }

    @Test
    void testUpdateScore() {
        scoreboard.startGame("Mexico", "Canada");
        scoreboard.updateScore("Mexico", "Canada", 2, 3);
        List<String> summary = scoreboard.getSummary();
        assertEquals("Mexico 2 - Canada 3", summary.get(0));
    }

    @Test
    void testGetSummary() {
        scoreboard.startGame("Mexico", "Canada");
        scoreboard.updateScore("Mexico", "Canada", 0, 5);

        scoreboard.startGame("Spain", "Brazil");
        scoreboard.updateScore("Spain", "Brazil", 10, 2);

        scoreboard.startGame("Germany", "France");
        scoreboard.updateScore("Germany", "France", 2, 2);

        scoreboard.startGame("Uruguay", "Italy");
        scoreboard.updateScore("Uruguay", "Italy", 6, 6);

        scoreboard.startGame("Argentina", "Australia");
        scoreboard.updateScore("Argentina", "Australia", 3, 1);

        List<String> summary = scoreboard.getSummary();
        assertEquals(5, summary.size());
        assertEquals("Uruguay 6 - Italy 6", summary.get(0));
        assertEquals("Spain 10 - Brazil 2", summary.get(1));
        assertEquals("Mexico 0 - Canada 5", summary.get(2));
        assertEquals("Argentina 3 - Australia 1", summary.get(3));
        assertEquals("Germany 2 - France 2", summary.get(4));
    }

    @ParameterizedTest
    @CsvSource({
            "Mexico, Canada",
            "Spain, Brazil",
            "Germany, France"
    })
    void testParameterizedStartGame(String homeTeam, String awayTeam) {
        scoreboard.startGame(homeTeam, awayTeam);
        List<String> summary = scoreboard.getSummary();
        assertTrue(summary.stream().anyMatch(match -> match.equals(homeTeam + " 0 - " + awayTeam + " 0")));
    }

    @ParameterizedTest
    @CsvSource({
            "Mexico, Canada, 1, 0",
            "Spain, Brazil, 3, 2",
            "Germany, France, 2, 2"
    })
    void testParameterizedUpdateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        scoreboard.startGame(homeTeam, awayTeam);
        scoreboard.updateScore(homeTeam, awayTeam, homeScore, awayScore);
        List<String> summary = scoreboard.getSummary();
        assertTrue(summary.stream().anyMatch(match -> match.equals(homeTeam + " " + homeScore + " - " + awayTeam + " " + awayScore)));
    }
}
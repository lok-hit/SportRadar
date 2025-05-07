import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class FootballWorldCupScoreboard {

    // Class to represent a match
    static class Match {
        String homeTeam;
        String awayTeam;
        int homeScore;
        int awayScore;
        long creationTime;

        public Match(String homeTeam, String awayTeam) {
            this.homeTeam = homeTeam;
            this.awayTeam = awayTeam;
            this.homeScore = 0;
            this.awayScore = 0;
            this.creationTime = System.nanoTime();
        }

        public int getTotalScore() {
            return homeScore + awayScore;
        }

        @Override
        public String toString() {
            return homeTeam + " " + homeScore + " - " + awayTeam + " " + awayScore;
        }
    }

    private final List<Match> matches = new ArrayList<>();

    // Start a new game
    public void startGame(String homeTeam, String awayTeam) {
        matches.add(new Match(homeTeam, awayTeam));
    }

    // Finish a game
    public void finishGame(String homeTeam, String awayTeam) {
        matches.removeIf(match -> match.homeTeam.equals(homeTeam) && match.awayTeam.equals(awayTeam));
    }

    // Update the score of a match
    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        for (Match match : matches) {
            if (match.homeTeam.equals(homeTeam) && match.awayTeam.equals(awayTeam)) {
                match.homeScore = homeScore;
                match.awayScore = awayScore;
                return;
            }
        }
    }

    // Get summary of matches ordered by total score
    public List<String> getSummary() {
        return matches.stream()
                .sorted(Comparator.comparingInt(Match::getTotalScore)
                        .reversed()
                        .thenComparingLong(match -> -match.creationTime))
                .map(Match::toString)
                .collect(Collectors.toList());
    }

    // Load matches from a file
    public void loadMatchesFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String homeTeam = parts[0].trim();
                    String awayTeam = parts[1].trim();
                    int homeScore = Integer.parseInt(parts[2].trim());
                    int awayScore = Integer.parseInt(parts[3].trim());
                    startGame(homeTeam, awayTeam);
                    updateScore(homeTeam, awayTeam, homeScore, awayScore);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading matches from file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        FootballWorldCupScoreboard scoreboard = new FootballWorldCupScoreboard();

        // Load matches from a file
        String filePath = "C:\\Lok-Hit\\SportRadar\\FootballScore\\exampleMatches.txt"; // Replace with your file path
        scoreboard.loadMatchesFromFile(filePath);

        // Print the summary
        List<String> summary = scoreboard.getSummary();
        summary.forEach(System.out::println);
    }
}
package org.example;

public class FootballWorldCupGame extends Game {

    public FootballWorldCupGame(String homeTeamName, String awayTeamName) {
        super(homeTeamName, awayTeamName);
    }

    @Override
    void updateScore(int homeTeamScore, int awayTeamScore) {
        checkUpdateScorePrerequisites(homeTeamScore, awayTeamScore);

        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
    }

    private void checkUpdateScorePrerequisites(int homeTeamScore, int awayTeamScore) {
        if (homeTeamScore < 0) {
            throw new IllegalArgumentException("Home team score must be zero or positive. Current value: " + homeTeamScore);
        }
        if (awayTeamScore < 0) {
            throw new IllegalArgumentException("Away team score must be zero or positive. Current value: " + awayTeamScore);
        }
    }

}

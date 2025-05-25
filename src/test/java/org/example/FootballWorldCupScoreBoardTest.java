package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class FootballWorldCupScoreBoardTest {

    @Test
    void startGame() {
        // given
        ScoreBoard scoreBoard = new FootballWorldCupScoreBoard();
        String homeTeam = "Mexico";
        String awayTeam = "Canada";

        // when
        Game game = scoreBoard.startGame(homeTeam, awayTeam);

        // then
        Assertions.assertEquals(0, game.getHomeTeamScore(), "Home team score should be equal to 0 at the begging of the game.");
        Assertions.assertEquals(0, game.getAwayTeamScore(), "Away team score should be equal to 0 at the begging of the game.");
    }

    @Test
    void updateScore() {
        // given
        ScoreBoard scoreBoard = new FootballWorldCupScoreBoard();
        String homeTeam = "Mexico";
        String awayTeam = "Canada";
        Game game = scoreBoard.startGame(homeTeam, awayTeam);

        Assertions.assertEquals(0, game.getHomeTeamScore(), "Prerequisites failed");
        Assertions.assertEquals(0, game.getAwayTeamScore(), "Prerequisites failed");

        // when
        game.updateScore(1, 0);

        // then
        Assertions.assertEquals(1, game.getHomeTeamScore(), "Home team score should be equal to 1");
        Assertions.assertEquals(0, game.getAwayTeamScore(), "Away team score should be equal to 0");

    }

    @Test
    void finishGame() {
        // given
        ScoreBoard scoreBoard = new FootballWorldCupScoreBoard();
        String homeTeam = "Mexico";
        String awayTeam = "Canada";
        Game game = scoreBoard.startGame(homeTeam, awayTeam);

        Assertions.assertNotNull(game, "Prerequisites failed");

        // when
        scoreBoard.finishGame(homeTeam, awayTeam);

        // then
        List<Game> summary = scoreBoard.getSummaryOfGamesByTotalScore();
        Assertions.assertFalse(summary.isEmpty());
    }

    @Test
    void getSummaryOfGamesByTotalScore() {
        // given
        ScoreBoard scoreBoard = new FootballWorldCupScoreBoard();
        Game argentinaAustraliaGame = scoreBoard.startGame("Argentina", "Australia");
        Game spainBrazilGame = scoreBoard.startGame("Spain", "Brazil");
        Game mexicoCanadaGame = scoreBoard.startGame("Mexico", "Canada");
        argentinaAustraliaGame.updateScore(3, 1);
        spainBrazilGame.updateScore(10, 2);
        mexicoCanadaGame.updateScore(0, 5);

        // when
        List<Game> summary = scoreBoard.getSummaryOfGamesByTotalScore();

        // then
        List<Game> expectedSummary = new ArrayList<>();
        // Descending order
        expectedGames.add(0, spainBrazilGame); // total: 12 goals
        expectedGames.add(1, mexicoCanadaGame); // total: 5 goals
        expectedGames.add(2, argentinaAustraliaGame); // total: 4 goals

        Assertions.assertIterableEquals(expectedSummary, summary);
    }

}

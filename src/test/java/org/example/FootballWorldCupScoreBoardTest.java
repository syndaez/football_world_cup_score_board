package org.example;

import org.example.footballworldcup.FootballWorldCupScoreBoard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

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

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = "  ")
    void startGame_wrongHomeTeam_throwsIllegalArgumentException(String homeTeam) {
        ScoreBoard scoreBoard = new FootballWorldCupScoreBoard();
        String awayTeam = "Canada";

        Assertions.assertThrows(IllegalArgumentException.class, () -> scoreBoard.startGame(homeTeam, awayTeam));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = "  ")
    void startGame_wrongAwayTeam_throwsIllegalArgumentException(String awayTeam) {
        ScoreBoard scoreBoard = new FootballWorldCupScoreBoard();
        String homeTeam = "Mexico";

        Assertions.assertThrows(IllegalArgumentException.class, () -> scoreBoard.startGame(homeTeam, awayTeam));
    }

    @Test
    void startGame_homeTeamIsCurrentlyPlaying_ThrowsIllegalStateException() {
        // given
        ScoreBoard scoreBoard = new FootballWorldCupScoreBoard();
        String homeTeam = "Mexico";
        String awayTeam = "Canada";

        // when
        scoreBoard.startGame(homeTeam, awayTeam);

        // then
        Assertions.assertThrows(IllegalStateException.class, () -> scoreBoard.startGame("Mexico", "Brazil"));
        Assertions.assertThrows(IllegalStateException.class, () -> scoreBoard.startGame(" Mexico ", "Brazil"));
        Assertions.assertThrows(IllegalStateException.class, () -> scoreBoard.startGame("mexico", "Brazil"));
    }

    @Test
    void startGame_awayTeamIsCurrentlyPlaying_ThrowsIllegalStateException() {
        // given
        ScoreBoard scoreBoard = new FootballWorldCupScoreBoard();
        String homeTeam = "Mexico";
        String awayTeam = "Canada";

        // when
        scoreBoard.startGame(homeTeam, awayTeam);

        // then
        Assertions.assertThrows(IllegalStateException.class, () -> scoreBoard.startGame("Brazil", "Canada"));
        Assertions.assertThrows(IllegalStateException.class, () -> scoreBoard.startGame("Brazil", " Canada "));
        Assertions.assertThrows(IllegalStateException.class, () -> scoreBoard.startGame("Brazil", "canada"));
    }

    @Test
    void startGame_homeAndAwayTeamsWithLeadingAndTrailingWhiteSpaces_AreTrimmed() {
        // given
        ScoreBoard scoreBoard = new FootballWorldCupScoreBoard();
        String homeTeam = " Mexico ";
        String awayTeam = " Canada ";

        // when
        Game game = scoreBoard.startGame(homeTeam, awayTeam);

        // then
        Assertions.assertEquals("Mexico", game.getHomeTeamName(), "Home team name should be trimmed");
        Assertions.assertEquals("Canada", game.getAwayTeamName(), "Away team name should be trimmed");
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
    void updateScore_NegativeHomeTeamScore_throwsIllegalArgumentException() {
        // given
        ScoreBoard scoreBoard = new FootballWorldCupScoreBoard();
        String homeTeam = "Mexico";
        String awayTeam = "Canada";
        Game game = scoreBoard.startGame(homeTeam, awayTeam);

        // when
        Assertions.assertThrows(IllegalArgumentException.class, () -> game.updateScore(-1, 0));
    }

    @Test
    void updateScore_NegativeAwayTeamScore_throwsIllegalArgumentException() {
        // given
        ScoreBoard scoreBoard = new FootballWorldCupScoreBoard();
        String homeTeam = "Mexico";
        String awayTeam = "Canada";
        Game game = scoreBoard.startGame(homeTeam, awayTeam);

        // when
        Assertions.assertThrows(IllegalArgumentException.class, () -> game.updateScore(0, -1));
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
        Assertions.assertTrue(summary.isEmpty());
    }

    @Test
    void finishGame_homeAndAwayTeamsWithWrongCaseOrLeadingTrailingWhiteSpaces_ShouldFinishGameProperly() {
        // given
        ScoreBoard scoreBoard = new FootballWorldCupScoreBoard();
        String homeTeam = "Mexico";
        String awayTeam = "Canada";
        Game game = scoreBoard.startGame(homeTeam, awayTeam);

        Assertions.assertNotNull(game, "Prerequisites failed");

        // when
        scoreBoard.finishGame(" mexico ", " canada ");

        // then
        List<Game> summary = scoreBoard.getSummaryOfGamesByTotalScore();
        Assertions.assertTrue(summary.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = "  ")
    void finishGame_wrongHomeTeam_throwsIllegalArgumentException(String homeTeam) {
        ScoreBoard scoreBoard = new FootballWorldCupScoreBoard();
        String awayTeam = "Canada";

        Assertions.assertThrows(IllegalArgumentException.class, () -> scoreBoard.finishGame(homeTeam, awayTeam));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = "  ")
    void finishGame_wrongAwayTeam_throwsIllegalArgumentException(String awayTeam) {
        ScoreBoard scoreBoard = new FootballWorldCupScoreBoard();
        String homeTeam = "Mexico";

        Assertions.assertThrows(IllegalArgumentException.class, () -> scoreBoard.finishGame(homeTeam, awayTeam));
    }

    @Test
    void finishGame_gameNotExists_throwsIllegalStateException() {
        ScoreBoard scoreBoard = new FootballWorldCupScoreBoard();
        String homeTeam = "Mexico";
        String awayTeam = "Canada";

        Assertions.assertThrows(IllegalStateException.class, () -> scoreBoard.finishGame(homeTeam, awayTeam));
        Assertions.assertThrows(IllegalStateException.class, () -> scoreBoard.finishGame(homeTeam, awayTeam));
        Assertions.assertThrows(IllegalStateException.class, () -> scoreBoard.finishGame(homeTeam, awayTeam));
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

        expectedSummary.add(argentinaAustraliaGame);
        expectedSummary.add(spainBrazilGame);
        expectedSummary.add(mexicoCanadaGame);

        org.assertj.core.api.Assertions.assertThat(summary)
                .containsExactlyInAnyOrderElementsOf(expectedSummary);
    }

    @Test
    void getSummaryOfGamesByTotalScore_noGames_returnsEmptyList() {
        // given
        ScoreBoard scoreBoard = new FootballWorldCupScoreBoard();

        // when
        List<Game> summary = scoreBoard.getSummaryOfGamesByTotalScore();

        // then
        org.assertj.core.api.Assertions.assertThat(summary)
                .isEmpty();
    }

    @Test
    void getSummaryOfGamesByTotalScore_OrderedByTotalScoreDesc() {
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

        expectedSummary.add(0, spainBrazilGame); // Total score: 12
        expectedSummary.add(1, mexicoCanadaGame); // Total score: 5
        expectedSummary.add(2, argentinaAustraliaGame); // Total score: 4

        org.assertj.core.api.Assertions.assertThat(summary)
                .containsExactlyElementsOf(expectedSummary);
    }

    @Test
    void getSummaryOfGamesByTotalScore_SameTotalScore_OrderedByAddedDateAsc() {
        // given
        ScoreBoard scoreBoard = new FootballWorldCupScoreBoard();
        Game argentinaAustraliaGame = scoreBoard.startGame("Argentina", "Australia");
        Game spainBrazilGame = scoreBoard.startGame("Spain", "Brazil");
        Game mexicoCanadaGame = scoreBoard.startGame("Mexico", "Canada");
        Game uruguayItaly = scoreBoard.startGame("Uruguay", "Italy");
        argentinaAustraliaGame.updateScore(3, 1);
        spainBrazilGame.updateScore(10, 2);
        mexicoCanadaGame.updateScore(0, 5);
        uruguayItaly.updateScore(6, 6);

        // when
        List<Game> summary = scoreBoard.getSummaryOfGamesByTotalScore();

        // then
        List<Game> expectedSummary = new ArrayList<>();

        expectedSummary.add(0, spainBrazilGame); // Total score: 12; Same total score as uruguayItaly but added earlier to the system
        expectedSummary.add(1, uruguayItaly); // Total score: 12; Same total score as spainBrazil but added later to the system
        expectedSummary.add(2, mexicoCanadaGame); // Total score: 5
        expectedSummary.add(3, argentinaAustraliaGame); // Total score: 4

        org.assertj.core.api.Assertions.assertThat(summary)
                .containsExactlyElementsOf(expectedSummary);
    }

}

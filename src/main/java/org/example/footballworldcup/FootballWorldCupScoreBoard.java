package org.example.footballworldcup;

import org.apache.commons.lang3.StringUtils;
import org.example.Game;
import org.example.ScoreBoard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FootballWorldCupScoreBoard implements ScoreBoard {

    private final List<Game> games = new ArrayList<>();

    private final Comparator<Game> gameComparator = Comparator.comparingInt(Game::getTotalScore)
            .reversed()
            .thenComparing(Game::getAddedDate);

    @Override
    public Game startGame(String homeTeam, String awayTeam) {
        checkStartGamePrerequisites(homeTeam, awayTeam);

        Game newGame = new FootballWorldCupGame(homeTeam.trim(), awayTeam.trim());
        games.add(newGame);
        return newGame;
    }

    private void checkStartGamePrerequisites(String homeTeam, String awayTeam) {
        if (StringUtils.isBlank(homeTeam)) {
            throw new IllegalArgumentException("Home team name is wrong");
        }
        if (StringUtils.isBlank(awayTeam)) {
            throw new IllegalArgumentException("Away team name is wrong");
        }
        if (isTeamPlaying(homeTeam)) {
            throw new IllegalStateException("Home team " + homeTeam + " is already playing");
        }
        if (isTeamPlaying(awayTeam)) {
            throw new IllegalStateException("Away team " + awayTeam + " is already playing");
        }
    }

    @Override
    public void finishGame(String homeTeam, String awayTeam) {
        checkFinishGamePrerequisites(homeTeam, awayTeam);

        games.removeIf(game -> game.getHomeTeamName().equalsIgnoreCase(homeTeam.trim())
                && game.getAwayTeamName().equalsIgnoreCase(awayTeam.trim()));
    }

    private void checkFinishGamePrerequisites(String homeTeam, String awayTeam) {
        if (StringUtils.isBlank(homeTeam)) {
            throw new IllegalArgumentException("Home team name is wrong");
        }
        if (StringUtils.isBlank(awayTeam)) {
            throw new IllegalArgumentException("Away team name is wrong");
        }
        if (isTeamPlaying(homeTeam) == false) {
            throw new IllegalStateException("Home team " + homeTeam + " is not playing");
        }
        if (isTeamPlaying(awayTeam) == false) {
            throw new IllegalStateException("Away team " + awayTeam + " is not playing");
        }
    }

    private boolean isTeamPlaying(String team) {
        return games.stream().anyMatch(game -> StringUtils.equalsAnyIgnoreCase(team.trim(), game.getHomeTeamName(), game.getAwayTeamName()));
    }

    @Override
    public List<Game> getSummaryOfGamesByTotalScore() {
        return games.stream().sorted(gameComparator).toList();
    }
}

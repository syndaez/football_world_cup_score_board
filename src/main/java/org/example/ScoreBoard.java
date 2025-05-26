package org.example;

import java.util.List;

public interface ScoreBoard {

    Game startGame(String homeTeam, String awayTeam);

    void finishGame(String homeTeam, String awayTeam);

    List<Game> getSummaryOfGamesByTotalScore();

}

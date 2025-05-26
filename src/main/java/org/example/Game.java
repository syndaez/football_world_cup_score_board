package org.example;

import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class Game {

    protected final String homeTeamName;
    protected final String awayTeamName;
    protected int homeTeamScore;
    protected int awayTeamScore;
    protected final LocalDateTime addedDate;

    protected Game(String homeTeamName, String awayTeamName) {
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
        this.homeTeamScore = 0;
        this.awayTeamScore = 0;
        this.addedDate = LocalDateTime.now();
    }

    abstract void updateScore(int homeTeamScore, int awayTeamScore);

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public int getAwayTeamScore() {
        return awayTeamScore;
    }

    public LocalDateTime getAddedDate() {
        return addedDate;
    }

    public int getTotalScore() {
        return homeTeamScore + awayTeamScore;
    }
}

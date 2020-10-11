package com.ex.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDateTime;

/**
 * The schedule class is a timestamp utility class that displays the time & team to play against
 * @param gameDay - the day/time to play on
 * @param scheduledTeam - the team to go against
 */
@JsonDeserialize(using = JSONScheduleDeserializer.class)
public class Schedule {
    private int id;
    private LocalDateTime gameDay;
    private String teamOne;
    private String teamTwo;
    private int scoreTeamOne;
    private int scoreTeamTwo;
    private String forfeit;

    public Schedule() {
        this.id = -1;
        this.gameDay = null;
        this.teamOne = null;
        this.teamTwo = null;
        this.scoreTeamOne = 0;
        this.scoreTeamTwo = 0;
        this.forfeit = null;
    }

    public Schedule(int id, LocalDateTime gameDay, String teamOne, String teamTwo, int scoreTeamOne,
                    int scoreTeamTwo, String forfeit) {
        this.id = id;
        this.gameDay = gameDay;
        this.teamOne = teamOne;
        this.teamTwo = teamTwo;
        this.scoreTeamOne = scoreTeamOne;
        this.scoreTeamTwo = scoreTeamTwo;
        this.forfeit = forfeit;
    }

    /* =================    GET & SET   ======================= */

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public LocalDateTime getGameDay() {
        return gameDay;
    }
    public void setGameDay(LocalDateTime gameDay) {
        this.gameDay = gameDay;
    }
    public String getTeamOne() {
        return teamOne;
    }
    public void setTeamOne(String teamOne) {
        this.teamOne = teamOne;
    }
    public String getTeamTwo() {
        return teamTwo;
    }
    public void setTeamTwo(String teamTwo) {
        this.teamTwo = teamTwo;
    }
    public int getScoreTeamOne() {
        return scoreTeamOne;
    }
    public void setScoreTeamOne(int scoreTeamOne) {
        this.scoreTeamOne = scoreTeamOne;
    }
    public int getScoreTeamTwo() {
        return scoreTeamTwo;
    }
    public void setScoreTeamTwo(int scoreTeamTwo) {
        this.scoreTeamTwo = scoreTeamTwo;
    }
    public String getForfeit() {
        return forfeit;
    }
    public void setForfeit(String forfeit) {
        this.forfeit = forfeit;
    }

    @Override
    public String toString() {
        return "'{\"id\":\""+getId()+"\", \"gameday\":\""+getGameDay().toString()+"\", \"teamone\":\""+getTeamOne()+
                "\", \"teamtwo\":\""+getTeamTwo()+"\", \"scoreteamone\":\""+getScoreTeamOne()+"\", \"scoreteamtwo\":\""+getScoreTeamTwo()+
                "\", \"forfeit\":\""+getForfeit()+"\"}'";
    }
}

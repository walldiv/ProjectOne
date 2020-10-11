package com.ex.model;

import java.util.Arrays;

/**
 * This object is primarily used for backend and calculations for front end UI
 * Mostly for website view purposes.  This is a seasonal league that will allow
 * for dynamic reuse for years to come.
 * @param city - The city this is used for
 * @param teams - the teams involved for a particular year
 * @param season - the year that this season falls under
 */
public class League {
    private String city;
    private Team[] teams;
    /* Format should be YYYY-MM-DD */
    private String season;

    public League() {
        city = "";
        teams = null;
        season = "";
    }
    public League(String city, Team[] teams, String season) {
        this.city = city;
        this.teams = teams;
        this.season = season;
    }

/* =================    GET & SET   ======================= */
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public Team[] getTeams() {
        return teams;
    }
    public void setTeams(Team[] teams) {
        this.teams = teams;
    }
    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    @Override
    public String toString() {
        return "League{" +
                "city='" + city + '\'' +
                ", teams=" + Arrays.toString(teams) +
                ", season='" + season + '\'' +
                '}';
    }
}

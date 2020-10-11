package com.ex.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class is for the Team entity
 *
 * @param name - the name of the Team
 * @param coach - the coach of the Team
 * @param players - an array containing the players of the team
 * @param schedule - the team's schedule
 * @param sponsor - the team's sponsor             *
 */
public class Team {

    private String name;
    private Person coach;
    private List<Player> players;
    private List<Schedule> schedule;
    private String sponsor;


    /** no arg constructor that sets the Team's String fields to empty strings
     * and the teams object fields to null
     *
     */
    public Team() {
        this.name = "";
        this.coach = null;
        this.players = new ArrayList<>();
        this.schedule = null;
        this.sponsor = "";
    }

    /** arged constructor that sets all of the teams feilds according to the data passed
     *
     * @param name
     * @param coach
     * @param players
     * @param schedule
     * @param sponsor
     */
    public Team(String name, Person coach, List<Player> players, List<Schedule> schedule, String sponsor) {
        this.name = name;
        this.coach = coach;
        this.players = players;
        this.schedule = schedule;
        this.sponsor = sponsor;
    }

    /**
     * This constructor creates a templated version of team with just the team name populated
     * used for database reads where a full team object isnt necessary - just the name
     */
    public Team(String name) {
        this.name = name;
        this.coach = null;
        this.players = new ArrayList<>();
        this.schedule = null;
        this.sponsor = "";
    }



    /* =================    GET & SET   ======================= */
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Person getCoach() { return coach; }
    public void setCoach(Person coach) { this.coach = coach; }
    public List<Player> getPlayers() { return players; }
    public void setPlayers(List<Player> players) { this.players = players; }
    public List<Schedule> getSchedule() { return schedule; }
    public void setSchedule(List<Schedule> schedule) { this.schedule = schedule; }
    public String getSponsor() { return sponsor; }
    public void setSponsor(String sponser) { this.sponsor = sponser; }

    @Override
    public String toString() {
        List<String> thesePlayers = new ArrayList<>();
        for (Player e : players) {
            thesePlayers.add(e.toString());
        }
        return "'{\"name\":\"" + name + "\", \"coach\":\"" + coach + "\", \"players\":" + thesePlayers + ", \"schedule\":[" + schedule + "], \"sponsor\":\"" + sponsor + "\"}'";
    }
}

package com.ex.dao;

import com.ex.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public interface CoachDAO {

    /**
     * This funct gets the logged in user as a coach type.  Getter for certain servlets related
     * to coache functionalities.
     * @param user - the logged in user variable from httpsession
     * @return - returns a type Person - which is what Coaches are modelled on
     * @throws Exception - SQL errors
     */
    public Person getCoach(User user) throws Exception;
    /**
     * Add a sponsor to the appropriate team.  Leave sponsor null if you want to erase the sponsor
     * @param sponsor - sponsor to add - leave null if you want to erase
     * @param team - the team to add sponsor too
     * @return - success of DAO call
     */
    public void addSponsor(Sponsor sponsor, Team team) throws Exception;

    /* Renames the team after season starts */
    public void renameTeam(String currentTeamName, String newName) throws Exception;

    /* Coach establishes a practice day for the team */
    public void setPracticeDay(LocalDateTime day, String team) throws Exception;

    /* Coach from one team needs to input scores into portal */
    public void addGameScore(int scheduleID, int finalScore, boolean isTeamOne) throws Exception;

    /* Coach needs to flag as forfeit for game */
    public void forfeitGame(int scheduleID, Team team) throws Exception;

    /* Coach needs to modify positions on the passed player */
    public void changePlayerPosition(Player player, Position position) throws Exception;

    /* Coach needs to add a player to the roster */
    public void addPlayerToTeam(Player player, Team team) throws Exception;

    /* Coach needs to remove a player from the team */
    public void removePlayerFromTeam(Player player, Team team) throws Exception;

    /* Getter function for getting schedule for team */
    public ArrayList<Schedule> getTeamSchedule(String team);
}

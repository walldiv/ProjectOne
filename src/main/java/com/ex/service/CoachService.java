package com.ex.service;

import com.ex.dao.CoachDAO;
import com.ex.dao.CoachDAOImpl_PGR;
import com.ex.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This service class is responsible for all things related to Coach CRUD
 * calls to the database.  Its a generic DAO interface caller - passing specific
 * DAO implementation SINGULARLY in this class - - allowing for interchangeability
 *  of database types in future by simply adding a new class type here for the following:
 * @param coachDao - this class archetype is for vendor-specific CoachDAO implementations
 */
public class CoachService {
    private CoachDAO coachDao;

    public CoachService() {
        this.coachDao = new CoachDAOImpl_PGR();  //change this impl for different vendor types
    }

    public CoachService(CoachDAO coachDao) {
        this.coachDao = coachDao;
    }

    public Person getCoach(User user) throws Exception {
        Person coach = null;
        try {
            coach = coachDao.getCoach(user);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return coach;
        }
    }

    /**
     * Add a sponsor to the appropriate team.  Leave sponsor null if you want to erase the sponsor
     * @param sponsor - sponsor to add - leave null if you want to erase
     * @param team - the team to add sponsor too
     * @return - success of DAO call
     */
    public boolean addSponsor(Sponsor sponsor, Team team) {
        try{
            coachDao.addSponsor(sponsor, team);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean renameTeam(String currentTeamName, String newName) throws Exception {
        try {
            coachDao.renameTeam(currentTeamName, newName);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setPracticeDay(LocalDateTime day, String team) {
        try{
            coachDao.setPracticeDay(day, team);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }    }

    public boolean addGameScore(int scheduleID, int finalScore, boolean isTeamOne) throws Exception {
        try{
            coachDao.addGameScore(scheduleID, finalScore, isTeamOne);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean forfeitGame(int scheduleID, Team team) throws Exception {
        try{
            coachDao.forfeitGame(scheduleID, team);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean changePlayerPosition(Player player, Position position) {
        try{
            coachDao.changePlayerPosition(player, position);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addPlayerToTeam(Player player, Team team) {
        try{
            coachDao.addPlayerToTeam(player, team);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removePlayerFromTeam(Player player, Team team) {
        try{
            coachDao.removePlayerFromTeam(player, team);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Schedule> getTeamSchedule(String team) {
        ArrayList<Schedule> schedule = new ArrayList<>();
        schedule = coachDao.getTeamSchedule(team);
        return schedule;
    }
}

package com.ex.service;

import com.ex.dao.GameDAO;
import com.ex.dao.GameDAOImpl_PGR;
import com.ex.model.Schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This service class is responsible for all things related to Games CRUD
 * calls to the database.  Its a generic DAO interface caller - passing specific
 * DAO implementation SINGULARLY in this class - - allowing for interchangeability
 *  of database types in future by simply adding a new class type here for the following:
 * @param gameDao - this class archetype is for vendor-specific CoachDAO implementations
 */
public class GameService {
    private GameDAO gameDao;

    public GameService() {
        this.gameDao = new GameDAOImpl_PGR();   //change this impl for different vendor types
    }

    public GameService(GameDAO gameDao) {
        this.gameDao = gameDao;
    }

    /* Retrieve a score/schedule on particular day */
    public List<Schedule> getScheduleOnDay(LocalDate day){
        List<Schedule> schedules = new ArrayList<>();
        schedules = gameDao.getScheduleOnDay(day);
        return schedules;
    }

    /* Get all scores/schedules */
    public List<Schedule> getAllSchedules(){
        List<Schedule> schedules = new ArrayList<>();
        return schedules;
    }

}

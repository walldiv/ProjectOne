package com.ex.dao;

import com.ex.model.Schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface GameDAO {
    /* Retrieve a score/schedule on particular day */
    public List<Schedule> getScheduleOnDay(LocalDate day);

    /* Get all scores/schedules */
    public List<Schedule> getAllSchedules();
}

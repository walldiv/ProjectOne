package com.ex.wrapper;

import com.ex.model.Person;
import com.ex.model.Schedule;

import java.util.ArrayList;
import java.util.List;

public class JacksonWrapper {
    public List<Schedule> schedules;
    public Person coach;

    public JacksonWrapper() {
        schedules = new ArrayList<>();
        coach = null;
    }

    public JacksonWrapper(ArrayList<Schedule> schedules) {
        this.schedules = schedules;
        this.coach = null;
    }

    public JacksonWrapper(List<Schedule> schedules, Person coach) {
        this.schedules = schedules;
        this.coach = coach;
    }

    @Override
    public String toString() {
        return "JacksonWrapper{" +
                "schedules=" + schedules +
                "coach=" + coach +
                '}';
    }
}

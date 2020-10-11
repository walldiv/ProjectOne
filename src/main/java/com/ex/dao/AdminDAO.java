package com.ex.dao;

import com.ex.model.*;

import java.util.List;

public interface AdminDAO {

    /* Gets all the users accounts from dbase for Admin to work on */
    public List<User> getAllUsers();

    /* Changes the user account permission type level */
    public void changeUserAccessLevel(User user, String accessLevel) throws Exception;

    /* Resets password for user to default password allowing for login/reset */
    public void resetPasswordToDefault(User user) throws Exception;

    /* Creates a team onto the database - typically called from StartSeason */
    public void createTeam(Team team) throws Exception;

    /* Assigns a player a team - typically called from StartSeason or CoachPortal::RecruitPlayer */
    public void setTeamOnPlayer(Player player, Team team) throws Exception;

    /* Assigns a coach to a team - typically called from StartSeason or CoachPortal::RecruitPlayer */
    public void setTeamOnCoach(Person coach, Team team) throws Exception;

    /* Writes the schedules to DB after creating the schedule from AdminService::CreateTeam function */
    public void createSeasonSchedules(List<Schedule> season) throws Exception;

    /* DEBUG - IF PROBLEMS EXIST - CLEAN SLATE THE SEASON */
    public void resetSeason() throws Exception;
}

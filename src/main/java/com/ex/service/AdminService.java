package com.ex.service;

import com.ex.dao.AdminDAO;
import com.ex.dao.AdminDAOImpl_PGR;
import com.ex.model.*;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class AdminService {
    private AdminDAO dao;
    private PersonService personService;
    ConfigVarsService configService;



    public AdminService() {
        dao = new AdminDAOImpl_PGR();
        personService = new PersonService();
        configService = new ConfigVarsService();
    }

    public AdminService(AdminDAO dao) {
        this.dao = dao;
        personService = new PersonService();
        configService = new ConfigVarsService();
    }

    /* Gets all the users accounts from dbase for Admin to work on */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            users = dao.getAllUsers();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return users;
        }
    }

    /* Changes the user account permission type level */
    public boolean changeUserAccessLevel(User user, String accessLevel) throws Exception{
        try{
            dao.changeUserAccessLevel(user, accessLevel);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* Resets password for user to default password allowing for login/reset */
    public boolean resetPasswordToDefault(User user) throws Exception{
        try{
            dao.resetPasswordToDefault(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* Attempst to start a season - checks to see if there is a minimum of 18 players (2 teams @ 9 per) to match a minimum of
        2 coaches.  Any remainder will be split randomly amongst the teams.
     */
    public boolean startSeason(LocalDate startDay, int seasonDuration) throws Exception {
        //1: GEt a list of all registered players failout if less than 18
        List<Player> players = new ArrayList<>();
        try {
            players = personService.getAllPlayers();
            if (players.size() < 18) {
                //System.out.println("AdminService - cant start season... not enough players");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //2: Get a list of all registered coaches fail out if less than 2
        List<Person> coaches = new ArrayList<>();
        try {
            coaches = personService.getAllCoaches();
            if (coaches.size() < 2) {
                //System.out.println("AdminService - cant start season... not enough coaches");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //3: Split players randomly amongst the number of coaches to even distribution
        int teamsize = (players.size() + coaches.size() - 1) / coaches.size();  //This rounds our number up to cover remainders
        System.out.printf("TOTAL # OF PLAYERS: %d\n ", players.size());
        System.out.printf("TEAM SIZE PER TEAM: %d \n", teamsize);
        System.out.printf("REMAINDER: %d\n", players.size() % coaches.size());
        Player playersOnTeam[][] = new Player[coaches.size()][teamsize];
        System.out.println("NUMBER OF TEAMS CREATED: " + playersOnTeam.length);
        do {
            for(int coachIndex = 0; coachIndex < coaches.size(); coachIndex++) {
                for (int teamIndex = 0; teamIndex < teamsize; teamIndex++) {
                    if(players.size() <= 0)
                        break;
                    Random rand = new Random();
                    int r = rand.nextInt(players.size());
                    System.out.printf("RANDOM:%d        PLAYERSIZE: %d \n",r , players.size());
                    Player tmp = players.get(r);
                    playersOnTeam[coachIndex][teamIndex] = tmp;
                    players.remove(tmp);
                }
            }
        } while (players.size() > 0);
        //System.out.printf("TEAM 1 SIZE: %d \n", playersOnTeam[0].length);
        //System.out.printf("TEAM 2 SIZE: %d \n", playersOnTeam[1].length);

        //4: Setup N# of teams depending on above parameters with coaches assigned to each team
        Team teams[] = new Team[coaches.size()];
        for(int itr = 0; itr < coaches.size(); itr++) {
            List<Player> tmpPlayers = new ArrayList(Arrays.asList(playersOnTeam[itr]));
            List<Schedule> tmpSchedule = new ArrayList<>();
            teams[itr] = new Team("Team"+itr, coaches.get(itr), tmpPlayers, tmpSchedule, null);
            coaches.get(itr).setTeam(teams[itr]);
//            for(Player e : tmpPlayers) {
            System.out.println("TMPPLAYERS INDEX: " + itr + "     TMPPLAYERS SIZE: " + tmpPlayers.size());
            for(int i = 0; i <tmpPlayers.size(); i++){
                if(tmpPlayers.get(i) == null)
                    break;
                System.out.println("SET TEAM ON " + tmpPlayers.get(i).getName() + "    INDEX: " + i);
                tmpPlayers.get(i).setTeam(teams[itr]);
            }
        }

        //Write coaches assigned team to database
        for(Person e : coaches) {
            setTeamOnCoach(e, e.getTeam());
        }

        //for each team - create team in DB and assign "team" field for each player in DB
        for (Team e : teams) {
            //System.out.println(e.toString());
            createTeam(e);
            for (Player a : e.getPlayers()) {
                setTeamOnPlayer(a, e);
                //System.out.printf("%s - %s \n", a.getName(), a.getTeam().getName());
            }
        }

        //#5: Create Schedules for each team - write these schedules to the database
        int schedulesPerWeek = (teams.length + 1)/ 2;    //round up on odd numbers
        List<Schedule> season = new ArrayList<>();
        for(int itr = 0; itr < seasonDuration; itr++){
            List<Schedule> tmp = new ArrayList<>();
            tmp = createSchedule(schedulesPerWeek, teams, startDay.plusWeeks(itr));
            season.addAll(tmp);
        }
//        System.out.println(Arrays.asList(season));
        //Write season to database
        dao.createSeasonSchedules(season);
        return true;
    }

    /* Creates a team onto the database - typically called from StartSeason */
    public boolean createTeam(Team team) throws Exception {
        try {
            dao.createTeam(team);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* Assigns a player a team - typically called from StartSeason or CoachPortal::RecruitPlayer */
    public boolean setTeamOnPlayer(Player player, Team team) throws Exception {
        try {
            dao.setTeamOnPlayer(player, team);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* Assigns a coach to a team - typically called from StartSeason or CoachPortal::RecruitPlayer */
    public boolean setTeamOnCoach(Person coach, Team team) throws Exception {
        try {
            dao.setTeamOnCoach(coach, team);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* Helper funct to produce a week's schedule */
    public List<Schedule> createSchedule(int numberToMake, Team teams[], LocalDate weekof) {
        String times[] = {configService.getProperty("gameTime1"), configService.getProperty("gameTime2"),
                configService.getProperty("gameTime3"), configService.getProperty("gameTime4")};
        int numberGamesPerWeekendDay = Integer.parseInt(configService.getProperty("numberGamesPerWeekendDay"));
        List<Schedule> weekSchedule = new ArrayList<>();
        List<Team> tmpTeamsList = new LinkedList<Team>(Arrays.asList(teams));
        do{
            for(int itr = 0; itr < numberToMake; itr++){
                if (tmpTeamsList.size() <= 0)
                    break;
                Random rand1 = new Random();
                Random rand2 = new Random();
                Team tmp1 = tmpTeamsList.get(0);
                Team tmp2 = tmpTeamsList.get(rand2.nextInt(tmpTeamsList.size()) );

                //If the teams happen to be the same .... go back an iteration & start over
                if(tmp1 == tmp2) {
                    itr--;
                    break;
                }

                //We calculate N# games a day from the config.cfg & use here to determine if we play on saturday or sunday
                LocalDate isSaturday = itr > numberGamesPerWeekendDay -1 ? weekof.plusDays(1) : weekof;
                LocalDateTime tmpTime = LocalDateTime.of(isSaturday, LocalTime.parse(times[itr]));
                Schedule tmp = new Schedule(
                        -1, tmpTime, tmp1.getName(), tmp2.getName(), 0, 0, null);
                weekSchedule.add(tmp);
                System.out.printf("SCHEDULE MADE: %s \n", tmp.toString());
                Team listToRemove[] = new Team[]{tmp1, tmp2};
                tmpTeamsList.removeAll(Arrays.asList(listToRemove));
//                if(tmpTeamsList.remove(tmp1))
//                    System.out.println("REMOVED " + tmp1.getName());
//                if(tmpTeamsList.remove(tmp2))
//                    System.out.println("REMOVED " + tmp2.getName());
//                System.out.println("TMPTEAMSLIST REMAINDER: " + Arrays.toString(tmpTeamsList));
            }
        } while(tmpTeamsList.size() > 1);

        //if We have 1 remaining team left - pair this up with a random team from the teams list
        System.out.printf("TMPTEAMSLIST SIZE AFTER REMOVAL: %d \n", tmpTeamsList.size());
        for (Team e : tmpTeamsList) {
            System.out.println(e.getName());
        }
        if(tmpTeamsList.size() >= 1) {
            while(true) {
                Random rand1 = new Random();
                Team tmp1 = teams[rand1.nextInt(teams.length)];

                //If the teams are the same from the random pick - exit out and keep going random till they dont match
                if(tmp1 == tmpTeamsList.get(0)) {
                    continue;
                }
                LocalDate isSaturday = numberToMake > numberGamesPerWeekendDay ? weekof.plusDays(1) : weekof;
                LocalDateTime tmpTime = LocalDateTime.of(isSaturday, LocalTime.parse(times[numberToMake % numberGamesPerWeekendDay]));
                Schedule tmp = new Schedule(
                        -1, tmpTime, tmp1.getName(), tmpTeamsList.get(0).getName(), 0, 0, null);
                weekSchedule.add(tmp);
                break;
            }
        }
        System.out.println("SEASON CREATION COMPLETED SUCCESSFULLY!");
        return weekSchedule;
    }

    /* DEBUG - IF PROBLEMS EXIST - CLEAN SLATE THE SEASON */
    public boolean resetSeason() throws Exception {
        try {
            dao.resetSeason();
            System.out.println("AdminService::resetSeason() - SEASON RESET SUCCESSFULLY");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("AdminService::resetSeason() - SEASON RESET FAILED");
        }
        return false;
    }
}

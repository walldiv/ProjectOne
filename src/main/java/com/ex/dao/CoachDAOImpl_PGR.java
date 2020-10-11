package com.ex.dao;

import com.ex.model.*;
import com.ex.service.ConnectionService;
import com.ex.service.PostgreSQLConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for all Database CRUD to our postgress database.  This class
 * should only EVER be called/invoked from the CoachService class.  It manually invokes a new
 * ConnectionService class of type PostgreSQLConnection - ONLY here.. offering singular invocation
 * and never fear of cross-vendor breakage for switching to a different database vendor
 */
public class CoachDAOImpl_PGR implements CoachDAO {
    private ConnectionService connectionSvc;

    public CoachDAOImpl_PGR() {
        connectionSvc = new PostgreSQLConnection();
    }

    @Override
    public Person getCoach(User user) throws Exception {
        Connection con = null;
        PreparedStatement stmt = null;
        Person coach = null;

        try {
            //initialize connection & prepare statement
            con = connectionSvc.getConnection();
            if (con != null) {
                String sql = "select * from coaches where coaches.userid = ?;";
                stmt = con.prepareStatement(sql);
                stmt.setInt(1, user.getId());
                //System.out.println(stmt);

                ResultSet rs = stmt.executeQuery();
                List<Person> persons = new ArrayList<>();
                while(rs.next()) {
                    Person tmp = new Person(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("phone"),
                            rs.getString("emergencyphone"),
                            PhoneCarrier.valueOf(rs.getString("phonecarrier")),
                            rs.getBoolean("allowsms"),
                            new Team(rs.getString("team")),
                            rs.getInt("userid")
                    );
                    persons.add(tmp);
                }

                //handle list - we should ONLY retrieve 1... error out if more than one or 0
                if(persons.size() <= 0 || persons.size() > 1) {
                    System.out.println("CoachDAOImpl_PGR::getCoach() - Error with retrieval from dbase.. ==0 or more than 1");
                }
                if (persons.size() == 1) {
                    coach = persons.get(0);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            return coach;
        }
    }

    @Override
    public void renameTeam(String currentTeamName, String newName) throws Exception{
        Connection con = null;
        PreparedStatement stmt = null;

        System.out.println("CoachDAOImpl_PGR::renameTeam() - STARTED RENAME TEAM FUNCTION");
        try {
            /*initialize connection & prepare statement - players/coaches tables are set to
                UPDATE CASCADE on their foreign key */
            con = connectionSvc.getConnection();
            if (con != null) {
                String sql = "UPDATE public.teams SET name=? WHERE name=?";
                stmt = con.prepareStatement(sql);
                stmt.setString(1, newName);
                stmt.setString(2, currentTeamName);
//                System.out.println(stmt);
                if(stmt.executeUpdate()<=0) {
                    System.out.println("ERROR - NO TEAM NAME WAS CHANGED");
                }

                //Now update the schedule manually for each teamnum - easier than trying to figure out cascade update
                String sql1 = "UPDATE public.schedule SET teamone=? WHERE teamone=?";
                stmt = con.prepareStatement(sql1);
                stmt.setString(1, newName);
                stmt.setString(2, currentTeamName);
//                System.out.println(stmt);
                if(stmt.executeUpdate()<=0) {
                    System.out.println("ERROR - NO TEAM NAME WAS CHANGED IN SCHEDULES - TEAMONE COLUMN");
                }

                //Team2names...
                String sql2 = "UPDATE public.schedule SET teamtwo=? WHERE teamtwo=?";
                stmt = con.prepareStatement(sql2);
                stmt.setString(1, newName);
                stmt.setString(2, currentTeamName);
//                System.out.println(stmt);
                if(stmt.executeUpdate()<=0) {
                    System.out.println("ERROR - NO TEAM NAME WAS CHANGED IN SCHEDULES - TEAMTWO COLUMN");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void addSponsor(Sponsor sponsor, Team team) throws Exception {
        Connection con = null;
        PreparedStatement stmt = null;
        String sponsorName = sponsor == null ? "NULL" : sponsor.getName();

        try {
            //initialize connection & prepare statement
            con = connectionSvc.getConnection();
            if (con != null) {
                String sql = "UPDATE public.teams SET sponsor=? WHERE name=?";
                stmt = con.prepareStatement(sql);
                stmt.setString(1, sponsorName);
                stmt.setString(2, team.getName());
                //System.out.println(stmt);
                if(stmt.executeUpdate()<=0) {
                    throw new Exception("ERROR - NO SPONSOR WAS ADDED");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void setPracticeDay(LocalDateTime day, String team) throws Exception {
        Connection con = null;
        PreparedStatement stmt = null;
        Timestamp time = Timestamp.valueOf(day);
        //System.out.println(parsed[0] + " " + parsed[1]);
        try {
            //initialize connection & prepare statement
            con = connectionSvc.getConnection();
            if (con != null) {
                String sql = "UPDATE public.teams SET practiceday = ? WHERE name = ?";
                stmt = con.prepareStatement(sql);
                stmt.setTimestamp(1, time);
                stmt.setString(2, team);
                System.out.println(stmt);
                if(stmt.executeUpdate()<=0) {
                    throw new Exception("ERROR - NO PRACTICEDAY WAS ADDED");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void addGameScore(int scheduleID, int finalScore, boolean isTeamOne) throws Exception {
        Connection con = null;
        PreparedStatement stmt = null;
        String sql = isTeamOne ? "UPDATE public.schedule SET scoreteamone = ? WHERE id = ?" :
                "UPDATE public.schedule SET scoreteamtwo = ? WHERE id = ?";
        try {
            //initialize connection & prepare statement
            con = connectionSvc.getConnection();
            if (con != null) {
                stmt = con.prepareStatement(sql);
                stmt.setInt(1, finalScore);
                stmt.setInt(2, scheduleID);
//                System.out.println(stmt);
                if(stmt.executeUpdate()<=0) {
                    throw new Exception("ERROR - NO SCORE WAS ADDED");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void forfeitGame(int scheduleID, Team team) throws Exception {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            //initialize connection & prepare statement
            con = connectionSvc.getConnection();
            if (con != null) {
                String sql = "UPDATE public.schedule SET forfeit = ? WHERE id = ?";
                stmt = con.prepareStatement(sql);
                stmt.setString(1, team.getName());
                stmt.setInt(2, scheduleID);
//                System.out.println(stmt);
                if(stmt.executeUpdate()<=0) {
                    throw new Exception("ERROR - NO FORFEIT WAS ADDED");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void changePlayerPosition(Player player, Position position) throws Exception {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            //initialize connection & prepare statement
            con = connectionSvc.getConnection();
            if (con != null) {
                String sql = "UPDATE public.players SET position = ? WHERE id = ?";
                stmt = con.prepareStatement(sql);
                stmt.setString(1, position.toString());
                stmt.setInt(2, player.getId());
                System.out.println(stmt);
                if(stmt.executeUpdate()<=0) {
                    throw new Exception("ERROR - NO POSITION WAS CHANGED");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void addPlayerToTeam(Player player, Team team) throws Exception {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            //initialize connection & prepare statement
            con = connectionSvc.getConnection();
            if (con != null) {
                String sql = "UPDATE public.players SET team = ? WHERE id = ?";
                stmt = con.prepareStatement(sql);
                stmt.setString(1, team.getName());
                stmt.setInt(2, player.getId());
                System.out.println(stmt);
                if(stmt.executeUpdate()<=0) {
                    throw new Exception("ERROR - NO PLAYER ADDED TO TEAM");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void removePlayerFromTeam(Player player, Team team) throws Exception {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            //initialize connection & prepare statement
            con = connectionSvc.getConnection();
            if (con != null) {
                String sql = "UPDATE public.players SET team = null WHERE id = ? AND team = ?";
                stmt = con.prepareStatement(sql);
                stmt.setInt(1, player.getId());
                stmt.setString(2, team.getName());
                System.out.println(stmt);
                if(stmt.executeUpdate()<=0) {
                    throw new Exception("ERROR - PLAYER NOT REMOVED");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public ArrayList<Schedule> getTeamSchedule(String team){
        ArrayList<Schedule> schedule = new ArrayList<>();

        Connection con = null;
        PreparedStatement stmt = null;

        try {
            //initialize connection & prepare statement
            con = connectionSvc.getConnection();
            if (con != null) {
                String sql = "SELECT * FROM schedule WHERE schedule.teamone = ? OR schedule.teamtwo = ? ORDER BY schedule.gameday";
                stmt = con.prepareStatement(sql);
                stmt.setString(1, team);
                stmt.setString(2, team);
//                System.out.println(stmt);
                ResultSet rs = stmt.executeQuery();
                while(rs.next()) {
                    Schedule tmp = new Schedule(
                            rs.getInt("id"),
                            rs.getTimestamp("gameday").toLocalDateTime(),
                            rs.getString("teamone"),
                            rs.getString("teamtwo"),
                            rs.getInt("scoreteamone"),
                            rs.getInt("scoreteamtwo"),
                            rs.getString("forfeit")
                    );
                    schedule.add(tmp);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            return schedule;
        }
    }
}

package com.ex.dao;

import com.ex.model.*;
import com.ex.service.ConnectionService;
import com.ex.service.PostgreSQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonDAOImpl_PGR implements PersonDAO{

    private ConnectionService connectionSvc;

    public PersonDAOImpl_PGR() {
        connectionSvc = new PostgreSQLConnection();
    }

    @Override
    public void addPerson(Person person) throws Exception {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            //initialize connection & prepare statement
            con = connectionSvc.getConnection();
            if (con != null) {
                String sql = "INSERT INTO public.coaches (name, phone, emergencyphone, phonecarrier, allowsms, userid) values(?, ?, ?, ?, ?, ?)";
                stmt = con.prepareStatement(sql);
                stmt.setString(1, person.getName());
                stmt.setString(2, person.getPhone());
                stmt.setString(3, person.getEmergencyPhone());
                stmt.setString(4, person.getPhonecarrier().toString());
                stmt.setBoolean(5, person.isAllowTxtMsg());
                stmt.setInt(6, person.getUserId());

//                System.out.println(stmt);
                if(stmt.executeUpdate()<=0) {
                    throw new Exception("No coach was created");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        try {
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addPlayer(Player player)throws Exception {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            //initialize connection & prepare statement
            con = connectionSvc.getConnection();
            if (con != null) {
                String sql = "INSERT INTO public.players (name, phone, emergencyphone, phonecarrier, allowsms, userid, parent, age, position) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
                stmt = con.prepareStatement(sql);
                stmt.setString(1, player.getName());
                stmt.setString(2, player.getPhone());
                stmt.setString(3, player.getEmergencyPhone());
                stmt.setString(4, player.getPhonecarrier().toString());
                stmt.setBoolean(5, player.isAllowTxtMsg());
                stmt.setInt(6, player.getUserId());
                stmt.setString(7, player.getParent());
                stmt.setInt(8, player.getAge());
                stmt.setString(9, "none");


//                System.out.println(stmt);
                if(stmt.executeUpdate()<=0) {
                    throw new Exception("No player was created");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        try {
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /* Gets list of all players */
    public List<Player> getAllPlayers(){
        Connection con = null;
        PreparedStatement stmt = null;
        List<Player> players = new ArrayList<>();

        try {
            //initialize connection & prepare statement
            con = connectionSvc.getConnection();
            if (con != null) {
                String sql = "SELECT * FROM public.players";
                stmt = con.prepareStatement(sql);
                //System.out.println(stmt);
                ResultSet rs = stmt.executeQuery();

                while(rs.next()) {
                    Player tmp = new Player(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("phone"),
                            rs.getString("emergencyphone"),
                            PhoneCarrier.valueOf(rs.getString("phonecarrier")),
                            rs.getBoolean("allowsms"),
                            new Team(rs.getString("team")),
                            rs.getInt("userid"),
                            rs.getString("parent"),
                            rs.getInt("age"),
                            Position.valueOf(rs.getString("position"))
                    );
                    players.add(tmp);
                }
            }
            else System.out.println("ERROR CONNECTING TO DATABASE");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            return players;
        }
    }

    /* Gets a list of all coaches */
    public List<Person> getAllCoaches(){
        Connection con = null;
        PreparedStatement stmt = null;
        List<Person> coaches = new ArrayList<>();

        try {
            //initialize connection & prepare statement
            con = connectionSvc.getConnection();
            if (con != null) {
                String sql = "SELECT * FROM public.coaches";
                stmt = con.prepareStatement(sql);
                //System.out.println(stmt);
                ResultSet rs = stmt.executeQuery();

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
                    coaches.add(tmp);
                }
            }
            else System.out.println("ERROR CONNECTING TO DATABASE");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            return coaches;
        }
    }
}

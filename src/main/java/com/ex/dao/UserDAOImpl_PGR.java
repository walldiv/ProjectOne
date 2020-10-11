package com.ex.dao;

import com.ex.model.User;
import com.ex.service.ConnectionService;
import com.ex.service.PostgreSQLConnection;
import com.ex.service.UserService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for all Database CRUD to our postgress database.  This class
 * should only EVER be called/invoked from the UserService class.  It manually invokes a new
 * ConnectionService class of type PostgreSQLConnection - ONLY here.. offering singular invocation
 * and never fear of cross-vendor breakage for switching to a different database vendor
 */
public class UserDAOImpl_PGR implements UserDAO {

    private ConnectionService connectionSvc;

    public UserDAOImpl_PGR() {
        connectionSvc = new PostgreSQLConnection();
    }

    /**
     * Attempts to login the passed args to the database
     *
     * @param username
     * @param passwordHashed
     * @return - User object of logged in successfully user... is Null if errors exist
     * @throws Exception - SQL problems OR multiple users retrieved.  There should ONLY ever be one
     *                   user that is returned.
     */
    @Override
    public User loginUser(String username, String passwordHashed) throws Exception {
        Connection con = null;
        PreparedStatement stmt = null;
        User user = null;
        List<User> users = new ArrayList<>();


        try {
            //initialize connection & prepare statement
            con = connectionSvc.getConnection();
            if (con != null) {
                String sql = "SELECT * FROM public.users WHERE username = ? AND password = ?";
                stmt = con.prepareStatement(sql);
                stmt.setString(1, username);
                stmt.setString(2, passwordHashed);
                System.out.println(stmt);

                //send prepared statement and apply resultset
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    int uid = rs.getInt("id");
                    String uname = rs.getString("username");
                    String upass = rs.getString("password");
                    String uaccess = rs.getString("useraccess");
                    String uemail = rs.getString("email");
                    User tmp = new User(uid, uname, upass, uemail, uaccess);
                    users.add(tmp);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (users.size() > 1 || users.size() <= 0) {
//            for(User e : users) {
//                System.out.printf("UserDAOImpl_PGR(ln55) -- USER RETRIEVED FROM DBASE: %s", e.getUsername());
//           };
            throw new Exception("SQL FETCH ERROR - NUMBER OF USERS RETURNED IS MORE THAN 1");
        } else {
            if (users.get(0).getUsername().equals(username) && users.get(0).getPassword().equals(passwordHashed)) {
                user = users.get(0);
            } else {
                System.out.printf("READ: %s  %s \n", users.get(0).getUsername(), users.get(0).getPassword());
                System.out.printf("PASSED: %s  %s \n", username, passwordHashed);
                throw new Exception("ERROR LOGGING IN");
            }
        }
        try {
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            return user;
        }
    }

    @Override
    public void addUser(User user) throws Exception {
        Connection con = null;
        PreparedStatement stmt = null;
        UserService userService = new UserService();
        String hashedPassword = userService.hashPassword(user.getPassword());
        try {
            //initialize connection & prepare statement
            con = connectionSvc.getConnection();
            if (con != null) {
                String sql = "INSERT INTO public.users (username, email, password, useraccess) values(?, ?, ?, ?)";
                stmt = con.prepareStatement(sql);
                stmt.setString(1, user.getUsername());
                stmt.setString(2, user.getEmail());
                stmt.setString(3, hashedPassword);
                stmt.setString(4, "user");
                //System.out.println(stmt);
                if(stmt.executeUpdate()<=0) {
                    throw new Exception("No user was created");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("No user was created");
        }

    }
}

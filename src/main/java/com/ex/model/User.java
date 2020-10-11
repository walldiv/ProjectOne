package com.ex.model;

import com.ex.dao.UserDAO;
import com.ex.service.UserService;

/**
 * This class is an entity class specifically used for HTTPSession API
 * to store the user information for session management during the user
 * existence throughout application.
 *
 * Safeguards in place will be a 5 minute duration timeout, as well a
 * hashed value of password both in the application as well on the
 * database - immediately hashed upon servlet POST request.
 *
 * @param username - the name the user logs in with
 * @param password - the password the user uses to log in with - HASHED IMMEDIATELY
 * @param email - the users email address
 * @param useraccess - the access level the user has throughout the application - admin, coach, player, user(default)
 */
public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    /* admin, coach, player, user(default) */
    private String useraccess;
    private int userId;

    public User() {
        this.id = -1;
        this.username = "DEFAULT";
        this.password = "DEFAULT";
        this.email = "DEFAULT@DEFAULT.COM";
        this.useraccess = "user";
    }


    public User(int id, String username, String password, String email, String useraccess) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.useraccess = useraccess;
    }

/* ==============   GETTERS & SETTERS   ============== */

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        UserService service = new UserService();
        this.username = service.hashPassword(username);
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getUseraccess() {
        return useraccess;
    }
    public void setUseraccess(String useraccess) {
        this.useraccess = useraccess;
    }

    @Override
    public String toString() {
        return "'{\"id\":\"" + id + "\", \"username\":\"" + username + "\", \"password\":\"" + password + "\", \"email\":\"" + email +
                "\", \"useraccess\":\"" + useraccess + "\"}'";
    }
}

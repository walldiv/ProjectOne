package com.ex.service;

import com.ex.dao.UserDAO;
import com.ex.dao.UserDAOImpl_PGR;
import com.ex.model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This service class is responsible for all things related to User CRUD
 * calls to the database.  Its a generic DAO interface caller - passing specific
 * DAO implementation SINGULARLY in this class - - allowing for interchangeability
 *  of database types in future by simply adding a new class type here for the following:
 * @param userDao - this class archetype is for vendor-specific UserDAO implementations
 */

public class UserService {
    private UserDAO userDao;

    public UserService() {
        this.userDao = new UserDAOImpl_PGR();   //change this impl for different vendor types
    }

    public UserService(UserDAO dao) {
        this.userDao = dao;
    }


    /**
     * @param password
     * @return - Hash password to be used for login or changing password
     */
    public String hashPassword(String password) {
        StringBuilder hash = new StringBuilder();
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = sha.digest(password.getBytes());
            char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f' };
            for(int itr = 0; itr < hashedBytes.length; itr++) {
                byte b = hashedBytes[itr];
                hash.append(digits[(b & 0xf0) >> 4]);
                hash.append(digits[b & 0x0f]);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash.toString();
    }

    /**
     *Attempt to login the passed username/password to the database
     * @param username
     * @param hashedpassword
     * @return - User object of logged in user - null if fails
     */
    public User loginUser(String username, String hashedpassword) {
        User user = null;
        try {
            user = userDao.loginUser(username, hashedpassword);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return user;
        }
    }

    public boolean addUser(User user){
        try{
            userDao.addUser(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

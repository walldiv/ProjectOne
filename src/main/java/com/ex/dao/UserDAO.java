package com.ex.dao;

import com.ex.model.User;

public interface UserDAO {
    //Login user
    public User loginUser(String username, String passwordHashed) throws Exception;
    public void addUser(User user)throws Exception;
}

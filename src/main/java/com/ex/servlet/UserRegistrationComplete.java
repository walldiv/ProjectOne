package com.ex.servlet;

import com.ex.model.User;
import com.ex.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UserRegistrationComplete extends HttpServlet {
    public UserRegistrationComplete() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp);
        System.out.println("UserRegistration - STARTED");
        String userName = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        System.out.println("IN USERNAME: " + userName);
        System.out.println("IN PASSWORD: " + password);
        System.out.println("IN EMAIL: " + email);

        User thisUser = new User(-1, userName, password, email, "user");
        UserService service = new UserService();
        boolean success = service.addUser(thisUser);
            //logic to return to index.html needs to be added
            //System.out.println("Add user successful");
            resp.setContentType("text/html");
            resp.setCharacterEncoding("utf-8");
            PrintWriter out = resp.getWriter();
            String response = "USER CREATED " + (success ? "SUCCESSFULLY!" : "UNSUCCESSFUL");
            out.print(response);
            out.flush();
            System.out.println("USER CREATED " + (success ? "SUCCESSFULLY!" : "UNSUCCESSFUL"));
    }
}

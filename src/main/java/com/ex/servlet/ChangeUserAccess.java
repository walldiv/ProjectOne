package com.ex.servlet;

import com.ex.model.User;
import com.ex.service.AdminService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChangeUserAccess extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("loggedUser");
        System.out.printf("USER FROM SESSION: %s \n", user);

        //If no one is logged in, user=null... we need to return out to safeguard nullpointer & simply go back to index.html
        if(user == null || !user.getUseraccess().equals("admin") ) {
            resp.sendRedirect("index.html");
            return;
        }

        //get list of all users in dbase
        AdminService service = new AdminService();
        List<User> users = new ArrayList<>();
        users = service.getAllUsers();

        //respond back with list
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");
        PrintWriter out = resp.getWriter();
        String jsonData = new Gson().toJson(users);
        out.print(jsonData);
        out.flush();

        System.out.println("CHANGE USER ACCESS CALLED");
//        for(User e : users)
//            System.out.println(e.toString());

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp);

        String[] checkBoxes = req.getParameterValues("userChecked");
        String newAccess = req.getParameter("userAccessGlobal");
        System.out.println(Arrays.toString(checkBoxes));
        AdminService service = new AdminService();
        for (String e : checkBoxes) {
            User tmp = new User(Integer.parseInt(e), null, null, null, null);
            try {
                if(!service.changeUserAccessLevel(tmp, newAccess))
                    System.out.println("FAILED CHANGE ACCESS ON: " + e + newAccess);
                System.out.println("CHANGED ACCESS FOR: " + e + newAccess);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

}

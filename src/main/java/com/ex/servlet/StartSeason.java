package com.ex.servlet;

import com.ex.model.User;
import com.ex.service.AdminService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;

public class StartSeason extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("loggedUser");
        System.out.printf("USER FROM SESSION: %s \n", user);

        //If no one is logged in, user=null... we need to return out to safeguard nullpointer & simply go back to index.html
        if(user == null || !user.getUseraccess().equals("admin") ) {
            resp.sendRedirect("index.html");
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);

        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("loggedUser");
        LocalDate practiceDay = LocalDate.parse(req.getParameter("practiceDay"));
        int duration = Integer.parseInt(req.getParameter("weeksDuration"));


        //Start service & invoke start season
        AdminService service = new AdminService();
        try {
            if(service.startSeason(practiceDay, duration));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("StartSeason::doPost() - UNABLE TO START SEASON");
        }
    }
}

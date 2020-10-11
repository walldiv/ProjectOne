package com.ex.servlet;

import com.ex.model.Person;
import com.ex.model.Schedule;
import com.ex.model.User;
import com.ex.service.CoachService;
import com.ex.wrapper.JacksonWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import javax.servlet.RequestDispatcher;
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

public class AddGameScore extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        System.out.println("DOGET CALLED");
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("loggedUser");
        System.out.printf("USER FROM SESSION: %s \n", user);

        //If no one is logged in, user=null... we need to return out to safeguard nullpointer & simply go back to index.html
        if(user == null || !user.getUseraccess().equals("coach") ) {
            resp.sendRedirect("index.html");
            return;
        }

        //Get a list of all schedules that pertain to our team - & pump back through response.
        ArrayList<Schedule> schedules = new ArrayList<>();
        CoachService service = new CoachService();
        Person coach = null;
        try {
             coach = service.getCoach(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(coach != null) {
            schedules = service.getTeamSchedule(coach.getTeam().getName());
            session.setAttribute("schedules", schedules);
            JacksonWrapper jswrap = new JacksonWrapper(schedules);
            ObjectMapper om = new ObjectMapper();
            String json = om.writeValueAsString(jswrap);
            resp.setContentType("application/json");
            resp.setStatus(200);
            resp.getWriter().write(json);
            System.out.println("AddGameScore::JSON RESPONSE - " + json);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);

        System.out.println("DO POST CALLED");
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("loggedUser");
        ArrayList<Schedule> schedules = (ArrayList<Schedule>)session.getAttribute("schedules");
        System.out.printf("USER FROM SESSION: %s \n", user);

        //If no one is logged in, user=null... we need to return out to safeguard nullpointer & simply go back to index.html
        if(user == null || !user.getUseraccess().equals("coach") ) {
            resp.sendRedirect("index.html");
            return;
        }

        String json = req.getParameter("schedulePassed");
        ObjectMapper om = new ObjectMapper();
        Integer blah = om.readValue(json, Integer.class);
        System.out.println("AddGameScore::doPost() - " + schedules.get(blah));
        session.setAttribute("schedule", schedules.get(blah));
        resp.sendRedirect("setupgamescore.html");

    }
}

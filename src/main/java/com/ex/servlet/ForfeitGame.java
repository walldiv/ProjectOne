package com.ex.servlet;

import com.ex.model.Person;
import com.ex.model.Schedule;
import com.ex.model.Team;
import com.ex.model.User;
import com.ex.service.CoachService;
import com.ex.wrapper.JacksonWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ForfeitGame extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);

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
            System.out.println("ForfeitGame::doGet() - Error retrieving coach");
        }
        if(coach != null) {
            session.setAttribute("coach", coach);
            schedules = service.getTeamSchedule(coach.getTeam().getName());
            session.setAttribute("schedules", schedules);
            JacksonWrapper jswrap = new JacksonWrapper(schedules, coach);
            ObjectMapper om = new ObjectMapper();
            String json = om.writeValueAsString(jswrap);
            resp.setContentType("application/json");
            resp.setStatus(200);
            resp.getWriter().write(json);
        }




    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("loggedUser");
        ArrayList<Schedule> schedule = (ArrayList<Schedule>)session.getAttribute("schedules");
        Person coach = (Person)session.getAttribute("coach");
        System.out.printf("USER FROM SESSION: %s \n", user);
        System.out.println("COACH FROM SESSION: " + coach.toString());

        //If no one is logged in, user=null... we need to return out to safeguard nullpointer & simply go back to index.html
        if(user == null || !user.getUseraccess().equals("coach") ) {
            resp.sendRedirect("index.html");
            return;
        }

        int index = Integer.parseInt(req.getParameter("schedulePassed"));
        if(coach != null && schedule.get(index) != null) {
            boolean success = false;
            CoachService service = new CoachService();
            try {
                Team tmpTeam = new Team(coach.getTeam().getName());
                success = service.forfeitGame(schedule.get(index).getId(), tmpTeam);
            } catch (Exception e) {
                e.printStackTrace();
            }
            resp.setContentType("text/html");
            resp.setCharacterEncoding("utf-8");
            PrintWriter out = resp.getWriter();
            String response = "RECORDED FORFEIT " + (success ? "SUCCESSFULLY!" : "UNSUCCESSFUL");
            out.print(response);
            out.flush();

            //now that we are done with them - remove these session variables
            session.removeAttribute("coach");
            session.removeAttribute("schedules");
            System.out.println(response);
        }

    }
}

package com.ex.servlet;

import com.ex.model.Schedule;
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

public class SetupGamescore extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("loggedUser");
        Schedule schedule = (Schedule)session.getAttribute("schedule");
        System.out.printf("USER FROM SESSION: %s \n", user);

        //If no one is logged in, user=null... we need to return out to safeguard nullpointer & simply go back to index.html
        if(user == null || !user.getUseraccess().equals("coach") ) {
            resp.sendRedirect("index.html");
            return;
        }

        if(schedule != null) {
            ObjectMapper om = new ObjectMapper();
            String json = om.writeValueAsString(schedule);
            resp.setContentType("application/json");
            resp.setStatus(200);
            resp.getWriter().write(json);
            System.out.println("SetupGameScore::JSON RESPONSE - " + json);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("loggedUser");
        Schedule schedule = (Schedule)session.getAttribute("schedule");
        System.out.printf("USER FROM SESSION: %s \n", user);

        //get the variables passed by form
        int score = Integer.parseInt(req.getParameter("scoreRecorded"));
        String teamselected = req.getParameter("chosenTeam");

        //If no one is logged in, user=null... we need to return out to safeguard nullpointer & simply go back to index.html
        if(user == null || !user.getUseraccess().equals("coach") ) {
            resp.sendRedirect("index.html");
            return;
        }

        //Finally lets form up the transaction and process to DBase
        CoachService service = new CoachService();
        boolean success = false;
        try {
            success = service.addGameScore(schedule.getId(), score, (teamselected.equals("teamone") ? true :false));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            resp.setContentType("text/html");
            resp.setCharacterEncoding("utf-8");
            PrintWriter out = resp.getWriter();
            String response = "RECORDED SCORE " + (success ? "SUCCESSFULLY!" : "UNSUCCESSFUL");
            out.print(response);
            out.flush();

            //now that we are done with them - remove these session variables
            session.removeAttribute("schedule");
            session.removeAttribute("schedules");
        }
    }
}

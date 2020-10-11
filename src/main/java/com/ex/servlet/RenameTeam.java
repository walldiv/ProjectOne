package com.ex.servlet;

import com.ex.model.Person;
import com.ex.model.User;
import com.ex.service.CoachService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class RenameTeam extends HttpServlet {
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
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);

        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("loggedUser");
        String newName = req.getParameter("newName");
        CoachService service = new CoachService();

        //Build up a coach object by checking agains tlogged in user from session converted to coach
        Person coach = null;
        try {
            coach = service.getCoach(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Finally transact a rename function
        boolean success = false;
        try {
            success = service.renameTeam(coach.getTeam().getName(), newName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            resp.setContentType("text/html");
            resp.setCharacterEncoding("utf-8");
            PrintWriter out = resp.getWriter();
            String response = "RENAME TEAM " + (success ? "SUCCESSFULLY!" : "UNSUCCESSFUL");
            out.print(response);
            out.flush();
            System.out.println("RENAME TEAM COMPLETED - " + (success ? "SUCCESSFULLY!" : "UNSUCCESSFUL"));
        }
    }
}

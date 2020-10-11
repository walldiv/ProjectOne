package com.ex.servlet;

import com.ex.model.Person;
import com.ex.model.User;
import com.ex.service.CoachService;
import com.google.gson.Gson;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The portal servlet acts as a intermediary fork for the first portion (GET) but reacts differently for POST
 * GET - this method is used as a fork int he road for traffic depending on the session variable 'loggedUser'.
 *      depending on the value of loggedUser's useraccess determines what page will be redirected to for portal functionality
 *
 * PUT - this method is called directly after the page loads on the redirect from GET via javascript.  This allows for variables
 *      to be established from either the session state, or anything else we need to pass in @ page load time.
 */
public class Portal extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        //get session variables
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("loggedUser");
        System.out.printf("USER FROM SESSION: %s", user);

        //If no one is logged in, user=null... we need to return out to safeguard nullpointer & simply go back to index.html
        if(user == null) {
            resp.sendRedirect("index.html");
            return;
        }

        //switch on session::loggedUser - redirect to appropriate page
        switch(user.getUseraccess()){
            case "admin":
//                req.setAttribute("message", "THIS USER");
//                RequestDispatcher disp = getServletContext().getRequestDispatcher("/adminportal.html");
//                disp.forward(req, resp);
                resp.sendRedirect("adminportal.html");
                break;
            case "coach":
                resp.sendRedirect("coachportal.html");
                break;
            case "player":
                resp.sendRedirect("playerportal.html");
                break;
            default:
                resp.sendRedirect("index.html");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp);
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("loggedUser");
        System.out.printf("USER FROM SESSION: %s", user);

        //If no one is logged in, user=null... we need to return out to safeguard nullpointer & simply go back to index.html
        if(user == null) {
            resp.sendRedirect("index.html");
            return;
        }
        /* switch on session::loggedUser - Allows to propogate a response header to send to the page
            allowing for custom data to be given to each respective portal page.
         */
        switch(user.getUseraccess()) {
            case "admin":
                break;
            case "coach":
                break;
            case "player":
                break;
            default:
                break;
        }

        //Create a JSON object for javascript to pickup and parse
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");
        PrintWriter out = resp.getWriter();
        String jsonData = new Gson().toJson(user);
        System.out.println("COMBINED WITH USER OBJECT: " + jsonData);
        out.print(jsonData);
        out.flush();
    }
}

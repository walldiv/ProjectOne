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

public class ResetSeason extends HttpServlet {
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
        else {
            boolean success = false;
            AdminService service = new AdminService();
            try {
                success = service.resetSeason();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //Create a JSON object for javascript to pickup and parse
                resp.setContentType("text/html");
                resp.setCharacterEncoding("utf-8");
                PrintWriter out = resp.getWriter();
                String response = "SEASON RESTARTED " + (success ? "SUCCESSFULLY!" : "UNSUCCESSFUL");
                out.print(response);
                out.flush();
            }
        }

    }
}

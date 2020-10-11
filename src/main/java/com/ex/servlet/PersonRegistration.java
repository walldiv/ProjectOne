package com.ex.servlet;

import com.ex.model.*;
import com.ex.service.PersonService;
import com.ex.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class PersonRegistration extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("loggedUser");
        System.out.printf("USER FROM SESSION: %s \n", user);

        //If no one is logged in, user=null... we need to return out to safeguard nullpointer & simply go back to index.html
        if(user == null) {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");
        resp.setStatus(400);
        PrintWriter out = resp.getWriter();
        String response = "PLEASE LOGIN TO THE SITE FIRST";
        out.print(response);
        out.flush();
        System.out.println("OPEN PERSON REGISTRATION FAILED: User not logged in");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp);
        System.out.println("Do post called");
//        Boolean isCoach = req.getParameter("userTypes").toLowerCase().equals("coach") ? true : false;
        String personType = req.getParameter("userTypes");
        System.out.println(personType);
        boolean isCoach = personType.toLowerCase().trim().equals("coach");
        String name = req.getParameter("firstName") + " " + req.getParameter("lastName");
        String phone = req.getParameter("phoneNumber");
        String emergencyPhone = req.getParameter("emergencyContact");
        PhoneCarrier carrier = PhoneCarrier.valueOf(req.getParameter("cellCarriers"));
        String sms = req.getParameter("allowTxtMsg");
        boolean allowSms = sms == null ? false : true;
        String parent = req.getParameter("parentName");
        String ageStr = (req.getParameter("playerAge"));
        int age = ageStr.isEmpty() ? -1 : Integer.parseInt(ageStr);

        Team team = null;

        //DEBUGS
        System.out.println("IS COACH: " + isCoach);
        System.out.println("NAME: " + name);
        System.out.println("PHONE: " + phone);
        System.out.println("EMERGENCY PHONE: " + emergencyPhone);
        System.out.println("PHONE CARRIER: " + carrier.toString());
        System.out.println("ALLOWSMS: " + allowSms);
        System.out.println("PARENT: " + parent);
        System.out.println("AGE: " + age);



        //get HTTPSession variables
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("loggedUser");

        boolean success = false;
        PersonService service = new PersonService();
        if(isCoach) {
            Person thisCoach = new Person(-1, name, phone, emergencyPhone, carrier, allowSms, team, user.getId());
            success = service.addCoach(thisCoach);
        }
        if(!isCoach) {
            Player thisPlayer = new Player(-1, name, phone, emergencyPhone, carrier, allowSms, team, user.getId(), parent, age, null);
            success = service.addPlayer(thisPlayer);
        }
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");
        PrintWriter out = resp.getWriter();
        String response = "REGISTERED FOR SEASON " + (success ? "SUCCESSFULLY!" : "UNSUCCESSFUL");
        out.print(response);
        out.flush();
        System.out.println("REGISTERED FOR SEASON - " + (success ? "SUCCESSFULLY!" : "UNSUCCESSFUL"));
    }
}

package com.jm.servlets;

import com.jm.PageGenerator;
import com.jm.models.User;
import com.jm.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RegistrationServlet extends HttpServlet {

    private UserService userService = UserService.getInstance();
    private PageGenerator pg = PageGenerator.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageVariables = Collections.synchronizedMap(new HashMap<>());
        pageVariables.put("email", "Type your email: ");
        pageVariables.put("password", "Create password: ");
        resp.setContentType("text/html");
        resp.getWriter().println(pg.getPage("registerPage.html", pageVariables));
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String pass1 = req.getParameter("password");
        Map<String, Object> pageVariables = Collections.synchronizedMap(new HashMap<>());
        resp.setContentType("text/html");

        if (email.isEmpty() || pass1.isEmpty()) {
            resp.setStatus(400);
            pageVariables.put("message", "Email and password should not be empty! Try one more time!");
            pageVariables.put("mapping", "/register");
            resp.getWriter().println(pg.getPage("info.html", pageVariables));
        } else {
                User user = new User(email, pass1);
                if (userService.isExistsThisUser(user)) {
                    resp.setStatus(200);
                    pageVariables.put("message", "User is already registered! Provide different registration data!");
                    pageVariables.put("mapping", "/register");
                    resp.getWriter().println(pg.getPage("info.html", pageVariables));
                } else {
                    userService.addUser(user);
                    resp.setStatus(200);
                    pageVariables.put("message", "User with email " + email + " was successfully registered! You can login now!");
                    pageVariables.put("mapping", "/login");
                    resp.getWriter().println(pg.getPage("info.html", pageVariables));
                }
            }
        }
    }
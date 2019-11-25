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

public class LoginServlet extends HttpServlet {
    private PageGenerator pg = PageGenerator.getInstance();
    private UserService userService = UserService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageVariables = Collections.synchronizedMap(new HashMap<>());
        String email = req.getParameter("email");
        String pass = req.getParameter("password");
        if (!email.equals("") && !pass.equals("")) {
            User user = new User(email, pass);
            if (userService.isExistsThisUser(user)) {
               if (userService.authUser(user)) {
                   resp.setStatus(200);
                   resp.setContentType("text/html");
                   resp.getWriter().println("User " + email + " logged successfully");
                } else {
                    resp.setStatus(400);
                    resp.setContentType("text/html");
                    pageVariables.put("message", "Wrong data! Try one more time!");
                    pageVariables.put("mapping", "/login");
                    resp.getWriter().println(pg.getPage("info.html", pageVariables));
                }
            } else {
                resp.setStatus(400);
                resp.setContentType("text/html");
                pageVariables.put("message", "User with such email is not registered!");
                pageVariables.put("mapping", "/login");
                resp.getWriter().println(pg.getPage("info.html", pageVariables));
            }
        } else {
            resp.setStatus(400);
            resp.setContentType("text/html");
            pageVariables.put("message", "Email or password field should not be empty! Please try again!");
            pageVariables.put("mapping", "/login");
            resp.getWriter().println(pg.getPage("info.html", pageVariables));
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageVariables = Collections.synchronizedMap(new HashMap<>());
        resp.getWriter().println(pg.getPage("authPage.html", pageVariables));
    }
}

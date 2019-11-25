package com.freshman4000.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MultiServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain;charset=utf-8");
        try {
            resp.setStatus(200);
            resp.getWriter().println(Integer.parseInt(req.getParameter("value")) * 2);
        } catch (IllegalArgumentException e) {
            resp.setStatus(400);
            resp.getWriter().println(0);
        }
    }
}

package com.freshman4000.servlets;

import com.freshman4000.utility.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ResultServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageVariables = Collections.synchronizedMap(new HashMap<>());
        pageVariables.put("message", "Go to registration or transaction link!");
        resp.setStatus(200);
        resp.setContentType("text/html");
        resp.getWriter().write(PageGenerator.getInstance().getPage("resultPage.html", pageVariables));
    }
}

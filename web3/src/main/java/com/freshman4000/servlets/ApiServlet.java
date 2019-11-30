package com.freshman4000.servlets;

import com.freshman4000.utility.PageGenerator;
import com.google.gson.Gson;
import com.freshman4000.utility.DBException;
import com.freshman4000.models.BankClient;
import com.freshman4000.services.BankClientService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ApiServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BankClientService bankClientService = new BankClientService();
        Map<String, Object> pageVariables = Collections.synchronizedMap(new HashMap<>());
        Gson gson = new Gson();
        String json = "";
        if (req.getPathInfo().contains("all")) {
            try {
                json = gson.toJson(bankClientService.getAllClient());
            } catch (DBException e) {
                resp.setStatus(500);
                pageVariables.put("message", "Database error! Try one more time!");
                resp.getWriter().println(PageGenerator.getInstance().getPage("resultPage.html", pageVariables));
            }
        } else {
            //todo corrected APIServlet
            try {
                json = gson.toJson(bankClientService.getClientByName(req.getParameter("name")));
            } catch (DBException e) {
                resp.setStatus(500);
                pageVariables.put("message", "Database error! Try one more time!");
                resp.getWriter().println(PageGenerator.getInstance().getPage("resultPage.html", pageVariables));
            }
        }
        resp.getWriter().write(json);
        resp.setStatus(200);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BankClientService bankClientService = new BankClientService();
        try {
            bankClientService.createTable();
            resp.setStatus(200);
        } catch (DBException e) {
            resp.setStatus(400);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BankClientService bankClientService = new BankClientService();
        if (req.getPathInfo().contains("all")){
            try {
                bankClientService.cleanUp();
            } catch (DBException e) {
                resp.setStatus(400);
            }
        }
    }
}

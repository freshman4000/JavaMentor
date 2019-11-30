package com.freshman4000.servlets;

import com.freshman4000.models.BankClient;
import com.freshman4000.services.BankClientService;
import com.freshman4000.utility.DBException;
import com.freshman4000.utility.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println(PageGenerator.getInstance().getPage("registrationPage.html", new HashMap<>()));

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageVariables = Collections.synchronizedMap(new HashMap<>());
        BankClientService bankClientService = new BankClientService();
        try {
            bankClientService.createTable();
        } catch (DBException e) {
            e.printStackTrace();
        }
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        long money;
        try {
            money = Long.parseLong(req.getParameter("money"));
        } catch (NumberFormatException e) {
            money = -1;
        }
        if (!name.isEmpty() && !password.isEmpty() && money > 0) {
            BankClient bankClient = new BankClient();
            try {
               bankClient = bankClientService.getClientByName(name);
            } catch (DBException e) {
                //response in case of exception
                pageVariables.put("message", "Database error! Try one more time!");
                resp.getWriter().println(PageGenerator.getInstance().getPage("result.html", pageVariables));
            }
            if (bankClient == null) {
                try {
                    bankClientService.addClient(new BankClient(name, password, money));
                    pageVariables.put("message", "Add client successful");
                    resp.getWriter().println(PageGenerator.getInstance().getPage("resultPage.html", pageVariables));
                } catch (DBException e) {
                    //Database SQL Exception
                    pageVariables.put("message", "Client not add");
                    resp.getWriter().println(PageGenerator.getInstance().getPage("resultPage.html", pageVariables));
                }
                //Client is already registered! Provide different data!
            } else {
                pageVariables.put("message", "Client not add");
                resp.getWriter().println(PageGenerator.getInstance().getPage("resultPage.html", pageVariables));
            }
        } else {
            if (name.isEmpty()) {
                pageVariables.put("message", "Name should not be empty! Please provide Name!");
            } else if (password.isEmpty()) {
                pageVariables.put("message", "Password should not be empty! Please provide Password!");
            } else if (money <= 0) {
                pageVariables.put("message", "Deposit amount should be positive! Please specify Amount!");
            }
            resp.getWriter().println(PageGenerator.getInstance().getPage("resultPage.html", pageVariables));
        }
    }
}

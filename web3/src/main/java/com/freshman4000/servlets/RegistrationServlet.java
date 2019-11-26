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
import java.util.HashMap;

public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println(PageGenerator.getInstance().getPage("registrationPage.html", new HashMap<>()));

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BankClientService bankClientService = new BankClientService();
        try {
            bankClientService.createTable();
        } catch (DBException e) {
            e.printStackTrace();
        }
        //todo check if we can handle exception here
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        Long money = Long.parseLong(req.getParameter("money"));
        if (!name.isEmpty() && !password.isEmpty() && money > 0) {
            try {
                new BankClientService().addClient(new BankClient(name, password, money));
            } catch (DBException e) {
                e.printStackTrace();
            }
        } else {}
    }
}

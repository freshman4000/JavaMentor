package com.freshamn4000.servlets;

import com.freshamn4000.controllers.Controller;
import com.freshamn4000.interfaces.ClientService;
import com.freshamn4000.models.User;
import com.freshamn4000.services.HibernateClientService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/update")
public class UpdateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("update_user.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ClientService<User, Long> clientService = Controller.getClientService(req, resp);
        String firstName = req.getParameter("username");
        String lastName = req.getParameter("lastname");
        String email = req.getParameter("email");
        String birthDate = req.getParameter("birthdate");
        String phone = req.getParameter("phone");
        User user = new User(firstName, lastName, email, birthDate, phone);
        try {
            clientService.updateUser(Long.parseLong(req.getParameter("id")), user);
        } catch (SQLException e) {
            req.setAttribute("message", "DB access problem! Try one more time!");
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        }
        resp.sendRedirect("/show");

    }
}

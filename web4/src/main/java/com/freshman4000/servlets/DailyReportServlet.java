package com.freshman4000.servlets;

import com.freshman4000.models.DailyReport;
import com.freshman4000.services.CarService;
import com.freshman4000.services.DailyReportService;
import com.google.gson.Gson;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DailyReportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        String report = null;
        if (req.getPathInfo().contains("all")) {
            report = gson.toJson(DailyReportService.getInstance().getAllDailyReports());
        } else if (req.getPathInfo().contains("last")) {
            report = gson.toJson(DailyReportService.getInstance().getLastReport());
        }
        resp.setStatus(200);
        resp.getWriter().println(report == null ? gson.toJson("There are no daily reports!") : report);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CarService.getInstance().deleteAll();
        DailyReportService.getInstance().deleteAll();
        resp.setStatus(200);
    }
}

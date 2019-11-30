package com.freshman4000.servlets;

import com.freshman4000.models.Car;
import com.freshman4000.services.DailyReportService;
import com.google.gson.Gson;
import com.freshman4000.services.CarService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        String json = gson.toJson(CarService.getInstance().getAllCars());
        resp.getWriter().println(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String brand = req.getParameter("brand");
        String model = req.getParameter("model");
        String licensePlate = req.getParameter("licensePlate");
        resp.setStatus(200);
        Gson gson = new Gson();
        //check for null values
        if (!brand.isEmpty() || !model.isEmpty() || !licensePlate.isEmpty()) {
            Car car = CarService.getInstance().getCar(brand, model, licensePlate);
            if (car != null) {
                CarService.getInstance().deleteCar(car);
                DailyReportService.getInstance().makeCurrentReport(car);
            } else {
                //if null values present or no car in DB
                String json = gson.toJson("no such car for sale!");
                resp.getWriter().println(json);
            }
        } else {
            //if null values present or no car in DB
            String json = gson.toJson("wrong input data!");
            resp.getWriter().println(json);
        }
    }
}
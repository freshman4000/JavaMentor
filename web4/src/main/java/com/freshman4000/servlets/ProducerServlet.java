package com.freshman4000.servlets;

import com.freshman4000.models.Car;
import com.freshman4000.services.CarService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProducerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String brand = req.getParameter("brand");
        String model = req.getParameter("model");
        String licensePlate = req.getParameter("licensePlate");
        Long price;
        try {
            price = Long.parseLong(req.getParameter("price"));
        } catch (IllegalArgumentException e) {
            price = null;
        }
        //check for null values
        if (!brand.isEmpty()
                && !model.isEmpty()
                && !licensePlate.isEmpty()
                && price != null && price > 0
                && CarService.getInstance().validateCar(brand)) {
            Car car1 = new Car(brand, model, licensePlate, price);
            CarService.getInstance().acceptCar(car1);
            resp.setStatus(200);
        } else {
            resp.setStatus(403);
        }
    }
}

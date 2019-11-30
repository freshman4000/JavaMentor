package com.freshman4000.services;

import com.freshman4000.dao.CarDao;
import com.freshman4000.models.Car;
import org.hibernate.SessionFactory;
import com.freshman4000.utility.DBHelper;

import java.util.List;

public class CarService {

    private static CarService carService;

    private SessionFactory sessionFactory;

    private CarService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static CarService getInstance() {
        if (carService == null) {
            carService = new CarService(DBHelper.getSessionFactory());
        }
        return carService;
    }
    public List<Car> getAllCars() {
        return new CarDao(CarService.getInstance().sessionFactory.openSession()).getAllCars();
    }
    public void acceptCar(Car car) {
        new CarDao(CarService.getInstance().sessionFactory.openSession()).acceptCar(car);
    }
    public Car getCar(String brand, String model, String licensePlate) {
        return new CarDao(CarService.getInstance().sessionFactory.openSession()).getCar(brand, model, licensePlate);
    }
    public void deleteCar(Car car) {
        new CarDao(CarService.getInstance().sessionFactory.openSession()).deleteCar(car);
    }
    public void deleteAll() {
        new CarDao(CarService.getInstance().sessionFactory.openSession()).deleteAll();
    }
    public boolean validateCar(String brand) {
       return new CarDao(CarService.getInstance().sessionFactory.openSession()).validateCar(brand);
    }



}

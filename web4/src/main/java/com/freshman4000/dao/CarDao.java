package com.freshman4000.dao;

import com.freshman4000.models.Car;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class CarDao {

    private Session session;

    public CarDao(Session session) {
        this.session = session;
    }

    public void acceptCar(Car car) {
        session.save(car);
        session.close();
    }

    public List<Car> getAllCars() {
        try {
            Query query = session.createQuery("from Car");
            return (List<Car>)query.list();
        } finally {
            session.close();
        }
    }

    public Car getCar(String brand, String model, String licensePlate) {
        try {
            Query query = session.createQuery("FROM Car c where c.brand= :brand AND c.model= :model AND c.licensePlate= :licensePlate");
            return (Car) query.setParameter("brand", brand).setParameter("model", model).setParameter("licensePlate", licensePlate).uniqueResult();
        } finally {
            session.close();
        }
    }
    //it doesn't work without transaction
    public void deleteCar(Car car) {
        Transaction tx = session.beginTransaction();
        session.delete(car);
        tx.commit();
        session.close();
    }

    public void deleteAll() {
        Query query = session.createQuery("DELETE FROM Car");
        query.executeUpdate();
        session.close();
    }

    public boolean validateCar(String brand) {
        Query query = session.createQuery("SELECT COUNT (*) FROM Car c where c.brand= :brand");
        Long result = (Long) query.setParameter("brand", brand).uniqueResult();
        session.close();
        return result < 10;
    }
}
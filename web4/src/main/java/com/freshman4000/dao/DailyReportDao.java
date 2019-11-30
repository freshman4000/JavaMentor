package com.freshman4000.dao;

import com.freshman4000.models.Car;
import com.freshman4000.models.CurrentReport;
import com.freshman4000.models.DailyReport;
import org.hibernate.*;

import java.util.List;

public class DailyReportDao {
    private Session session;

    public DailyReportDao(Session session) {
        this.session = session;
    }

    public List<DailyReport> getAllDailyReport() {
        List<DailyReport> dailyReports = session.createQuery("FROM DailyReport").list();
        session.close();
        return dailyReports;
    }

    public void deleteAll() {
        Query query = session.createQuery("DELETE FROM DailyReport");
        query.executeUpdate();
        session.close();
    }

    public void makeCurrentReport(Car car) {
        CurrentReport cr = new CurrentReport(car);
        session.save(cr);
        session.close();
    }

    public void makeDailyReport() {
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("SELECT new DailyReport(SUM(price), COUNT(id)) FROM CurrentReport");
        DailyReport result = (DailyReport) query.uniqueResult();
        if (result.getSoldCars() == null) {
            result.setSoldCars(0L);
        }
        if (result.getEarnings() == null) {
            result.setEarnings(0L);
        }
        session.save(result);
        Query query1 = session.createQuery("DELETE FROM CurrentReport");
        query1.executeUpdate();
        tx.commit();
        session.close();
    }

    public DailyReport getLastReport() {
        Query query = session.createQuery("FROM DailyReport WHERE id = (SELECT MAX(id) FROM DailyReport)");
        return (DailyReport) query.uniqueResult();
    }
}
package com.freshman4000.services;

import com.freshman4000.dao.CarDao;
import com.freshman4000.dao.DailyReportDao;
import com.freshman4000.models.Car;
import com.freshman4000.models.DailyReport;
import org.hibernate.SessionFactory;
import com.freshman4000.utility.DBHelper;

import java.util.List;

public class DailyReportService {

    private static DailyReportService dailyReportService;

    private SessionFactory sessionFactory;

    private DailyReportService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static DailyReportService getInstance() {
        if (dailyReportService == null) {
            dailyReportService = new DailyReportService(DBHelper.getSessionFactory());
        }
        return dailyReportService;
    }

    public List<DailyReport> getAllDailyReports() {
        return new DailyReportDao(sessionFactory.openSession()).getAllDailyReport();
    }
    public void deleteAll() {
        new DailyReportDao(DailyReportService.getInstance().sessionFactory.openSession()).deleteAll();
    }

    public DailyReport getLastReport() {

        return new DailyReportDao(sessionFactory.openSession()).getLastReport();
    }

    public void makeDailyReport() {
        new DailyReportDao(sessionFactory.openSession()).makeDailyReport();
    }
    public void makeCurrentReport(Car car) {
        new DailyReportDao(sessionFactory.openSession()).makeCurrentReport(car);
    }
}

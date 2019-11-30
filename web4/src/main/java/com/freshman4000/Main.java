package com.freshman4000;

import com.freshman4000.models.Car;
import com.freshman4000.servlets.CustomerServlet;
import com.freshman4000.servlets.DailyReportServlet;
import com.freshman4000.servlets.NewDayServlet;
import com.freshman4000.servlets.ProducerServlet;
import com.freshman4000.utility.DBHelper;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class Main {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        CustomerServlet customerServlet = new CustomerServlet();
        ProducerServlet producerServlet = new ProducerServlet();
        DailyReportServlet reportServlet = new DailyReportServlet();
        NewDayServlet newDayServlet = new NewDayServlet();
        ServletContextHandler sch = new ServletContextHandler(ServletContextHandler.SESSIONS);
        sch.addServlet(new ServletHolder(customerServlet), "/customer");
        sch.addServlet(new ServletHolder(producerServlet), "/producer");
        sch.addServlet(new ServletHolder(reportServlet), "/report/*");
        sch.addServlet(new ServletHolder(newDayServlet), "/newday");

        server.setHandler(sch);
        server.start();
        server.join();
    }
}

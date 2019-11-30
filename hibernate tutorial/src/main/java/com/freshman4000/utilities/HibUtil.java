package com.freshman4000.utilities;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibUtil {

    private final static SessionFactory sessionFactory = buildSessionFactory();

    public static SessionFactory buildSessionFactory() {

        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

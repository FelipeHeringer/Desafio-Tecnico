package com.desafio.tecnico.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConfig {

    private static HibernateConfig instance;
    private SessionFactory sessionFactory;

    private HibernateConfig() {
        this.sessionFactory = buildSessionFactory();
    }

    public static HibernateConfig getInstance() {
        if (instance == null) {
            instance = new HibernateConfig();
        }
        return instance;
    }

    
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }

    private SessionFactory buildSessionFactory() {
        return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    }
}

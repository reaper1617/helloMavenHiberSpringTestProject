package com.gerasimchuk.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactorySingleton {
    private  volatile static   SessionFactory sessionFactory = null;

    public static SessionFactory getSessionFactoryInstance() {
        if (sessionFactory == null){
            synchronized (SessionFactorySingleton.class) {
                sessionFactory = new Configuration().configure().buildSessionFactory();
            }
        }


        return sessionFactory;
    }


    private SessionFactorySingleton() {
    }
}

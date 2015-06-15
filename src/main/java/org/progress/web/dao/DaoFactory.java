package org.progress.web.dao;

public class DaoFactory {

    public static WorkersDao getWorkersDao() {
        return new WorkersDao();
    }

    public static ApartamentsDao getApartamentsDao() {
        return new ApartamentsDao();
    }

    public static NewsDao getNewsDao() {
        return new NewsDao();
    }
}

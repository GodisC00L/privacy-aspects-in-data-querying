package com.dpv.finalendproj.service;

import com.dpv.finalendproj.dao.DatabaseDao;
import com.dpv.finalendproj.model.QueryFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {

    private final DatabaseDao databaseDao;

    @Autowired
    public DatabaseService(@Qualifier("db") DatabaseDao databaseDao) {
        this.databaseDao = databaseDao;
    }

    public int getKValue() {
        return databaseDao.getKValue();
    }

    public void setKValue(int newK) {
        databaseDao.setKValue(newK);
    }

    public double getAverageVelocity(QueryFormat qf) {
        return databaseDao.getAverageVelocity(qf);
    }

}

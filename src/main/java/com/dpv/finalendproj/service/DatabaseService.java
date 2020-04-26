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

    public int setKValue(int newK) {
        return databaseDao.setKValue(newK);
    }

    public double getAverageVelocity(QueryFormat qf) {
        return databaseDao.getAverageVelocity(qf);
    }

    public double getMinX() {
        return databaseDao.getMinX();
    }

    public double getMinY() {
        return databaseDao.getMinY();
    }

    public double getMaxX() {
        return databaseDao.getMaxX();
    }
    public double getMaxY() {
        return databaseDao.getMaxY();
    }
    public int getNumOfVehicles(double timestamp) {return databaseDao.getNumOfVehicles(timestamp);}

    public double getMaxVelocityForRange(QueryFormat qf){
        return databaseDao.getMaxVelocityForRange(qf.getTimestamp(), qf.getXRange());
    }

    public double getMinVelocityForRange(QueryFormat qf){
        return databaseDao.getMinVelocityForRange(qf.getTimestamp(), qf.getXRange());
    }
}

package com.dpv.finalendproj.dao;

import com.dpv.finalendproj.model.QueryFormat;

public interface DatabaseDao {

    double getAverageVelocity(QueryFormat qf);

    int getKValue();

    int setKValue(int newK);

    double getMinX();

    double getMinY();

    double getMaxX();

    double getMaxY();

    int getNumOfVehicles(double timestamp);
}

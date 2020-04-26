package com.dpv.finalendproj.dao;

import com.dpv.finalendproj.model.QueryFormat;
import org.javatuples.Pair;

public interface DatabaseDao {

    double getAverageVelocity(QueryFormat qf);

    int getKValue();

    int setKValue(int newK);

    double getMinX();

    double getMinY();

    double getMaxX();

    double getMaxY();

    int getNumOfVehicles(double timestamp);

    double getMaxVelocityForRange(double timestamp, Pair<Double, Double> xRange);

    double getMinVelocityForRange(double timestamp, Pair<Double, Double> xRange);
}

package com.dpv.finalendproj.dao;

import com.dpv.finalendproj.model.Database;
import com.dpv.finalendproj.model.QueryFormat;
import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("db")
public class DatabaseDataAccessService implements DatabaseDao {

    private static final Database db = new Database();
    Logger logger = LoggerFactory.getLogger(DatabaseDataAccessService.class);


    @Override
    public double getAverageVelocity(QueryFormat qf) {
        Pair<Pair<Double, Double>, Pair<Double, Double>> area = qf.getArea();

        if(!isInRange(area)){
            logger.warn("area not in range!");
            return -1;
        }

        Pair<Double, Double> xRange = new Pair<>(area.getValue0().getValue0(), area.getValue1().getValue0());

        Pair<Double, Double> yRange = new Pair<>(area.getValue0().getValue1(), area.getValue1().getValue1());
        if (yRange.getValue0() > yRange.getValue1()){
            swap(yRange);
        }
        return db.getAvgVelocity(qf.getTimestamp(), new Pair<>(xRange, yRange));
    }

    private void swap(Pair<Double, Double> pair) {
        double temp = pair.getValue0();
        pair = pair.setAt0(pair.getValue1());
        pair = pair.setAt1(temp);
    }

    @Override
    public int getKValue() {
        return db.getK();
    }

    @Override
    public int setKValue(int newK) {
        if(newK > 0) {
            db.setK(newK);
        } else {
            logger.warn("New K value is less then 0!");
        }
        return db.getK();
    }

    @Override
    public double getMinX() {
        return db.getMin_X();
    }

    @Override
    public double getMinY() {
        return db.getMin_Y();
    }

    @Override
    public double getMaxX() {
        return db.getMax_X();
    }

    @Override
    public double getMaxY() {
        return db.getMax_Y();
    }

    @Override
    public int getNumOfVehicles(double timestamp) {
        return db.getNumOfVehicles(timestamp);
    }

    boolean isInRange(Pair<Pair<Double, Double>, Pair<Double, Double>> area) {
        return isInRangeX(area.getValue0().getValue0()) && isInRangeY(area.getValue0().getValue1())
                && isInRangeX(area.getValue1().getValue0()) && isInRangeY(area.getValue1().getValue1());
    }

    boolean isInRangeX(double x) {
        return x <= db.getMax_X() && x >= db.getMin_X();
    }

    boolean isInRangeY(double y) {
        return y <= db.getMax_Y() && y >= db.getMin_Y();
    }
}

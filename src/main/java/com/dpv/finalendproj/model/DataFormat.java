package com.dpv.finalendproj.model;

public class DataFormat implements Comparable<DataFormat>{

    private final String carId;
    final double timestamp;
    final double x;
    final double y;
    private final double velocity;
    double sumToIndex;

    public DataFormat(String carId, double timestamp, double x, double y, double velocity) {
        this.carId = carId;
        this.timestamp = timestamp;
        this.x = x;
        this.y = y;
        this.velocity = velocity;
        this.sumToIndex = velocity;
    }

    public String getCarId() {
        return carId;
    }

    public double getTimestamp() {
        return timestamp;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getVelocity() {
        return velocity;
    }

    public double getSumToIndex() {
        return sumToIndex;
    }

    public void setSumToIndex(double sumToIndex) {
        this.sumToIndex = sumToIndex;
    }

    @Override
    public int compareTo(DataFormat dataFormat) {
        return Double.compare(this.x, dataFormat.x);
    }
}

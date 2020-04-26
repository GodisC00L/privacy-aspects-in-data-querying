package com.dpv.finalendproj.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.javatuples.Pair;

import javax.validation.constraints.NotNull;

public class QueryFormat {
    @NotNull
    final double timestamp;
    @NotNull
    final Pair<Pair<Double, Double>, Pair<Double, Double>> area;

    public QueryFormat(@JsonProperty("timestamp") Double timestamp,
                       @JsonProperty("x1") Double x1,
                       @JsonProperty("y1") Double y1,
                       @JsonProperty("x2") Double x2,
                       @JsonProperty("y2") Double y2) {
        this.timestamp = timestamp;
        Pair<Double, Double> p1 = new Pair<>(x1, y1);
        Pair<Double, Double> p2 = new Pair<>(x2, y2);
        this.area = new Pair<>(p1, p2);
    }

    public double getTimestamp() {
        return timestamp;
    }

    public Pair<Pair<Double, Double>, Pair<Double, Double>> getArea() {
        return area;
    }

    public Pair<Double, Double> getXRange() {
        return new Pair<>(area.getValue0().getValue0(), area.getValue1().getValue0());
    }

    @Override
    public String toString() {
        return "QueryFormat{" +
                "timestamp=" + timestamp +
                ", area=" + area +
                '}';
    }
}

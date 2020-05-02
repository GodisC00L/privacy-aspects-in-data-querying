package com.dpv.finalendproj.model;

import org.javatuples.Pair;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static com.dpv.finalendproj.model.Util.printProgressBar;

/**
 * Database structure:
 *      HashMap: Key:       Double Timestamp
 *               Value:     BST yTs
 *
 *      BST:     Node:      double Y
 *                          DataArr xList
 */
public class Database {
    private final HashMap<Double, BST> db;
    private double min_X = 0, max_X = 0, min_Y = 0, max_Y = 0;

    private int k = 3;

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public double getMin_X() {
        return min_X;
    }

    public double getMax_X() {
        return max_X;
    }

    public double getMin_Y() {
        return min_Y;
    }

    public double getMax_Y() {
        return max_Y;
    }

    public Database() {
        this.db = new HashMap<>();
        try {
            WorkingWithDataset ds = new WorkingWithDataset();
            ds.fillDb(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void addToDb(DataFormat df) {
        updateMinMax(df);
        BST tsRoot = db.getOrDefault(df.timestamp, null);

        if(null != tsRoot) {
            tsRoot.insert(df);
        } else {
            tsRoot = new BST(df);
            db.put(df.timestamp, tsRoot);
        }
    }

    private void updateMinMax(DataFormat df) {
        if (df.x > this.max_X || this.max_X == 0)
            this.max_X = df.x;
        if (df.x < this.min_X || this.min_X == 0)
            this.min_X = df.x;
        if (df.y > this.max_Y || this.max_Y == 0)
            this.max_Y = df.y;
        if (df.y < this.min_Y || this.min_Y == 0)
            this.min_Y = df.y;
    }

    /**
     * Balance BST tree
     */
    void balanceBST() {
        long size = db.size();
        long num = 0;
        System.out.println("\nStart Balancing...");
        for(BST yTree : db.values()) {
            num++;
            yTree.balance();
            yTree.addSumAndMergeLists();
            int precent = (int)((num * 100)/size);
            printProgressBar(precent);
        }
    }

    public double getAvgVelocity(double timestamp, Pair<Pair<Double,Double>, Pair<Double,Double>> ranges) {
        double result = -1;

        Pair<Double, Integer> velocityNumOfElements = getVelocityInRange(timestamp, ranges);
        if(velocityNumOfElements != null && velocityNumOfElements.getValue1() >= this.k)
            result =  velocityNumOfElements.getValue0();
        return result;
    }

    /*
     * 1. Find first y BST in Y range.
     * 2. Find indexes of xList that fit to X range
     * 3. Go through sub xList:
     *      3.1 if Y in Y range:
     *          sum Velocity and increment counter
     * 4. check if counter fits k
     * 5. return */
    Pair<Double, Integer> getVelocityInRange(double timestamp, Pair<Pair<Double, Double>, Pair<Double, Double>> ranges) {
        BST relevantYBst = db.get(timestamp);
        Pair<Double, Double> xRange = ranges.getValue0();
        Pair<Double, Double> yRange = ranges.getValue1();
        Pair<Double, Double> yBSTRange = new Pair<>(relevantYBst.getMinY(), relevantYBst.getMaxY());
        int counter = 0;

        if(!isInRange(yBSTRange, yRange.getValue0())) {
            yRange = yRange.setAt0(yBSTRange.getValue0());
        }

        if(!isInRange(yBSTRange, yRange.getValue1())) {
            yRange = yRange.setAt1(yBSTRange.getValue1());
        }

        Node relevantSubTree = relevantYBst.getRelevantSubTree(yRange);
        Pair<Integer, Integer> subXListIndexes = getSubXList(xRange, relevantSubTree.xList);

        double avgVelocity = 0;

        for (int i = subXListIndexes.getValue0(); i <= subXListIndexes.getValue1(); i++) {
            if(isInRange(yRange, relevantSubTree.xList.get(i).y)) {
                counter++;
                avgVelocity += relevantSubTree.xList.get(i).getVelocity();
            }
        }
        if(counter == 0)
            return new Pair<>(-1.0, counter);
        return new Pair<>(avgVelocity/counter, counter);
    }

    Pair<Integer, Integer> getSubXList(Pair<Double, Double> xRange, List<DataFormat> dataArr) {
        Pair<Integer, Integer> result = new Pair<>(-1, -1);
        result = result.setAt0(closestNumber(dataArr, xRange.getValue0(), false));
        result = result.setAt1(closestNumber(dataArr, xRange.getValue1(), true));
        return result;
    }

    boolean isInRange(Pair<Double, Double> range, double test){
        return test <= range.getValue1() && test >= range.getValue0();
    }


    /**
     * Find the index of the closest lower value to target for upper bound
     * and index of the closest highest value to target for lower bound
     * @param target, boolean upper
     * @return index of closest lower value
     */
    private int closestNumber(List<DataFormat> xList,double target, boolean upper) {
        int start = 0;
        int end = xList.size() - 1;
        int mid;

        if(target < xList.get(start).x)
            return start;
        if(target > xList.get(end).x)
            return end;

        while (start + 1 < end) {
            mid = start + (end - start) / 2;
            if (xList.get(mid).x == target) {
                return mid;
            } else if (mid - 1 >= 0 && xList.get(mid - 1).x <= target && target < xList.get(mid).x) {
                return !upper && xList.get(mid - 1).x != target ? mid : mid - 1;
            } else if (mid + 1 < xList.size() && xList.get(mid).x < target && target <= xList.get(mid + 1).x) {
                return upper && xList.get(mid + 1).x != target ? mid : mid + 1;
            } else if (xList.get(mid).x < target) {
                start = mid;
            } else {
                end = mid;
            }
        }
        return (target - xList.get(start).x) < (xList.get(end).x - target) ? start : end;
    }

    public int getNumOfVehicles(double timestamp) {
        return db.get(timestamp).getNumOfVehicles();
    }

    /**
     * Finds and returns the maximum velocity in a given range on a given timestamp
     * @param timestamp
     * @param xRange
     * @return maxVelocity
     */
    public double getMaxVelocityForRange(double timestamp, Pair<Double, Double> xRange) {
        double res = -1;
        Node relevantYTreeRoot = db.get(timestamp).getRoot();
        List<DataFormat> xList = relevantYTreeRoot.getxList();
        Pair<Double, Double> xTreeLimits = new Pair<>(xList.get(0).x, xList.get(xList.size() - 1).x);
        if(isInRange(xTreeLimits, xRange.getValue0()) && isInRange(xTreeLimits, xRange.getValue1())) {
            Pair<Integer, Integer> subXListIndexes = getSubXList(xRange, xList);

            for(int i = subXListIndexes.getValue0(); i <= subXListIndexes.getValue1(); i++){
                if(res < xList.get(i).getVelocity()) {
                    res = xList.get(i).getVelocity();
                }
            }
        }
        return res;
    }

    /**
     * Finds and returns the minimum velocity in a given range on a given timestamp
     * @param timestamp
     * @param xRange
     * @return minVelocity
     */
    public double getMinVelocityForRange(double timestamp, Pair<Double, Double> xRange) {
        double res = Double.MAX_VALUE;
        Node relevantYTreeRoot = db.get(timestamp).getRoot();
        List<DataFormat> xList = relevantYTreeRoot.getxList();
        Pair<Double, Double> xTreeLimits = new Pair<>(xList.get(0).x, xList.get(xList.size() - 1).x);
        if(isInRange(xTreeLimits, xRange.getValue0()) && isInRange(xTreeLimits, xRange.getValue1())) {
            Pair<Integer, Integer> subXListIndexes = getSubXList(xRange, xList);

            for(int i = subXListIndexes.getValue0(); i <= subXListIndexes.getValue1(); i++){
                if(res > xList.get(i).getVelocity()) {
                    res = xList.get(i).getVelocity();
                }
            }
        }
        return res;
    }

}

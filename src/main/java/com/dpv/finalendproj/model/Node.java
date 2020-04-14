package com.dpv.finalendproj.model;

import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;

public class Node {
    final double y;
    List<DataFormat> xList;
    Node left;
    Node right;

    public Node(DataFormat df) {
        this.y = df.getY();
        this.xList = new ArrayList<DataFormat>();
        this.xList.add(df);
        this.left = null;
        this.right = null;
    }

    public double getY() {
        return y;
    }

    public List<DataFormat> getxList() {
        return xList;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    boolean isLeaf() {
        return left == null && right == null;
    }

    public Node getNodeInRange(Pair<Double, Double> yRange) {
        if(yRange.getValue0() <= y && y <= yRange.getValue1())
            return this;
        if(yRange.getValue0() > y)
            return this.right.getNodeInRange(yRange);
        if(yRange.getValue1() < y)
            return this.left.getNodeInRange(yRange);
        return null;
    }
}

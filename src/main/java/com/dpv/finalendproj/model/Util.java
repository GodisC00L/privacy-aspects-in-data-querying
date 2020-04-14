package com.dpv.finalendproj.model;

public class Util {
    public static void printProgressBar(int percent){
        StringBuilder bar = new StringBuilder("[");
        for(int i = 0; i < 50; i++){
            if( i < (percent/2)){
                bar.append("=");
            } else if( i == (percent/2)) {
                bar.append(">");
            } else {
                bar.append(" ");
            }
        }


        bar.append("]   ").append(percent).append("%     ");
        System.out.print("\r" + bar.toString());
    }
}

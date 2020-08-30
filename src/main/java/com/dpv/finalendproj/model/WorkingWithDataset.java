package com.dpv.finalendproj.model;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.Scanner;

import static com.dpv.finalendproj.model.Util.printProgressBar;

class WorkingWithDataset {
    private Scanner dataSetScanner;
    private PrintWriter targetList;
    private FileInputStream datasetInputStream;
    private final long dataSetSize;

    private final String dataSetPath = "src/main/resources/fixedVelocities_40_MB.txt";
    //private final String dataSetPath = "src/main/resources/fixedVelocities_18000_22000.txt";
    //private final String dataSetPath = "../../SegalProjectV2/Server/koln.tr";
    private final String dataTargetListPath = "src/main/resources/fixedVelocities_40_MB_target.csv";

    WorkingWithDataset() throws FileNotFoundException {
        File dataSetFile = new File(dataSetPath);
        datasetInputStream = new FileInputStream(dataSetFile);
        dataSetSize = dataSetFile.length();

        if(!(new File(dataTargetListPath)).exists()) {
            targetList = new PrintWriter(dataTargetListPath);
        }
        dataSetScanner = new Scanner(datasetInputStream);
    }

    void fillDb(Database db) throws IOException {
        String[] splitted;
        DataFormat df;
        DecimalFormat df2 = new DecimalFormat("#.##");
        long total = 0;

        // Prepare file for writing
        if(targetList != null) {
            String sb = "Timestamp,X,Y,Particular Velocity,Avg Set Velocity,Attack Time for K: [ms]," +
                    "1,2,4,8,16,32,64,128,256,512,1024,2048,4096" + "\n";
            targetList.write(sb);
        }
        System.out.println("\nBuilding DB");
        while(dataSetScanner.hasNextLine()) {
            String data = dataSetScanner.nextLine();
            total += data.getBytes(StandardCharsets.UTF_8).length;
            splitted = data.split(" ");
            df = new DataFormat(splitted[1],
                    Double.parseDouble(splitted[0]),
                    Double.parseDouble(splitted[2]),
                    Double.parseDouble(splitted[3]),
                    Double.parseDouble(df2.format(Double.parseDouble(splitted[4])))
            );
            db.addToDb(df);
            if(targetList != null)
                writeToTargetList(df);

            int percent = (int)((total * 100)/ dataSetSize);
            printProgressBar(percent);
        }
        dataSetScanner.close();
        datasetInputStream.close();
        if(targetList != null)
            targetList.close();
        db.balanceBST();
    }

    public void cutFile(int fromTs, int toTs) throws FileNotFoundException {
        String inputLine;
        String[] splited;
        PrintWriter writer = new PrintWriter("src/main/resources/fixedVelocities_" + fromTs + "_" + toTs +".txt");
        System.out.println("\nCutting files\n");
        int size = toTs - fromTs;
        while(true) {
            inputLine = dataSetScanner.nextLine();
            splited = inputLine.split(" ");
            if(Double.parseDouble(splited[0]) == fromTs)
                break;
        }

        while(dataSetScanner.hasNextLine()) {
            inputLine = dataSetScanner.nextLine();
            splited = inputLine.split(" ");
            double ts = Double.parseDouble(splited[0]);
            if(ts == toTs+1)
                break;
            writer.println(inputLine);
            int precent = (int)((ts-fromTs)*100 / size);
            printProgressBar(precent);
        }
        writer.close();
    }

    private void writeToTargetList(DataFormat df){
        targetList.write((df.timestamp + "," + df.x + "," + df.y + "\n"));
    }
}

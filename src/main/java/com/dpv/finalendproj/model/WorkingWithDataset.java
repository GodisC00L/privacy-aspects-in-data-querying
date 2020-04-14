package com.dpv.finalendproj.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.Scanner;

import static com.dpv.finalendproj.model.Util.printProgressBar;

class WorkingWithDataset {
    private Scanner dataSetScanner;
    private PrintWriter targetList;
    private final long dataSetSize;

    private final String dataSetPath = "src/main/resources/fixedVelocities_40_MB.txt";
    private final String dataTargetListPath = "src/main/resources/fixedVelocities_40_MB_target.csv";

    WorkingWithDataset() throws FileNotFoundException {
        File dataSetFile = new File(dataSetPath);
        FileInputStream datasetInputStream = new FileInputStream(dataSetFile);
        dataSetSize = dataSetFile.length();

        if(!(new File(dataTargetListPath)).exists()) {
            targetList = new PrintWriter(dataTargetListPath);
        }
        dataSetScanner = new Scanner(datasetInputStream);
    }

    void fillDb(Database db) {
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
        if(targetList != null)
            targetList.close();
        db.balanceBST();
    }

    private void writeToTargetList(DataFormat df){
        targetList.write((df.timestamp + "," + df.x + "," + df.y + "\n"));
    }
}

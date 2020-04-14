package com.dpv.finalendproj.api;

import com.dpv.finalendproj.model.QueryFormat;
import com.dpv.finalendproj.service.DatabaseService;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/db")
@RestController
public class DatabaseController {

    private final DatabaseService databaseService;

    @Autowired
    public DatabaseController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }


    public void getKValue() {
        databaseService.getKValue();
    }

    @PostMapping
    public void setKValue(@RequestBody int newK) {
        databaseService.setKValue(newK);
    }

    @PostMapping("/avgVelocity")
    public double getAverageVelocity(@RequestBody QueryFormat qf) {
        return databaseService.getAverageVelocity(qf);
        /*return databaseService.getAverageVelocity(new QueryFormat(
                (double) 1000,1.3, 3.0,1.6, 3.0));*/
    }
}

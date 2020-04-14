package com.dpv.finalendproj.api;

import com.dpv.finalendproj.model.QueryFormat;
import com.dpv.finalendproj.service.DatabaseService;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RequestMapping("api/v1/db")
@RestController
public class DatabaseController {

    private final DatabaseService databaseService;

    @Autowired
    public DatabaseController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @GetMapping("/getK")
    public int getKValue() {
        return databaseService.getKValue();
    }

    @PutMapping("/setK")
    public void setKValue(@Valid @NotNull @RequestBody int newK) {
        databaseService.setKValue(newK);
    }

    @PostMapping("/avgVelocity")
    public double getAverageVelocity(@Valid @NotNull @RequestBody QueryFormat qf) {
        return databaseService.getAverageVelocity(qf);
    }
}

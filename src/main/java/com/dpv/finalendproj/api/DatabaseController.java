package com.dpv.finalendproj.api;

import com.dpv.finalendproj.dao.DatabaseDataAccessService;
import com.dpv.finalendproj.model.QueryFormat;
import com.dpv.finalendproj.service.DatabaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;

@RequestMapping("api/v1/db")
@RestController
public class DatabaseController {

    private final DatabaseService databaseService;
    Logger logger = LoggerFactory.getLogger(DatabaseController.class);

    @Autowired
    public DatabaseController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @CrossOrigin
    @GetMapping("/getK")
    public int getKValue() {
        return databaseService.getKValue();
    }

    @CrossOrigin
    @PutMapping("/setK")
    public int setKValue(@Valid @NotNull @RequestBody int newK) {
        return databaseService.setKValue(newK);
    }

    @CrossOrigin
    @PostMapping("/avgVelocity")
    public double getAverageVelocity(@Valid @NotNull @RequestBody QueryFormat qf) {
        logger.info(qf.toString());
        return databaseService.getAverageVelocity(qf);
    }

    @CrossOrigin
    @GetMapping(path = "/getMinMaxValues", produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, Object> getMinMaxValues() {
        double minX = databaseService.getMinX();
        double maxX = databaseService.getMaxX();
        double minY = databaseService.getMinY();
        double maxY = databaseService.getMaxY();

        HashMap<String, Object> map = new HashMap<>();
        map.put("minX", minX);
        map.put("maxX", maxX);
        map.put("minY", minY);
        map.put("maxY", maxY);

        return map;
    }

    @CrossOrigin
    @PostMapping("/numOfVehicles")
    public int getNumOfVehicles(@Valid @NotNull @RequestBody double timestamp) {
        return databaseService.getNumOfVehicles(timestamp);
    }

    @CrossOrigin
    @PostMapping("/getMaxVelocity")
    public double getMaxVelocity(@Valid @NotNull @RequestBody QueryFormat qf) {
        logger.info(qf.toString());
        return databaseService.getMaxVelocityForRange(qf);
    }

    @CrossOrigin
    @PostMapping("/getMinVelocity")
    public double getMinVelocity(@Valid @NotNull @RequestBody QueryFormat qf) {
        logger.info(qf.toString());
        return databaseService.getMinVelocityForRange(qf);
    }
}

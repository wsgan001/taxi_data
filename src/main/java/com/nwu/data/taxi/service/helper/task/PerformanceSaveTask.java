package com.nwu.data.taxi.service.helper.task;

import com.nwu.data.taxi.domain.model.Performance;
import com.nwu.data.taxi.domain.repository.PerformanceRepository;
import com.nwu.data.taxi.service.helper.Task;
import com.nwu.data.taxi.service.helper.model.Grid;
import com.nwu.data.taxi.service.helper.model.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class PerformanceSaveTask implements Task {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private PerformanceRepository performanceRepository;
    private List<Vehicle> vehicles;
    private int recommenderType;

    public PerformanceSaveTask(PerformanceRepository performanceRepository, List<Vehicle> vehicles, int recommenderType) {
        this.performanceRepository = performanceRepository;
        this.vehicles = vehicles;
        this.recommenderType = recommenderType;
    }

    @Override
    public void execute(HashMap<Long, List<Task>> tasks, long currentTime, HashMap<Integer, Grid> graph) {
        List<Performance> performances = new ArrayList<>();
        logger.info("Saving Performance! :)");
        for (Vehicle v : vehicles) {
            performances.add(new Performance(v, new Date(currentTime * 1000), recommenderType));
        }
        performanceRepository.save(performances);
    }
}

package com.nwu.data.taxi.service;

import com.nwu.data.taxi.domain.model.Performance;
import com.nwu.data.taxi.domain.repository.PerformanceRepository;
import com.nwu.data.taxi.service.helper.Config;
import com.nwu.data.taxi.service.helper.model.PerformancePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PerformanceService {
    @Autowired
    private PerformanceRepository performanceRepository;

    public Iterable<Performance> getResult(String date, int recommenderType) {
        return performanceRepository.findByDateAndType(date, recommenderType);
    }

    public List<PerformancePage> getPerformancePage(String date) {
        List<PerformancePage> performancePages = new ArrayList<>();
        performancePages.add(new PerformancePage(performanceRepository, date, Config.MY));
        performancePages.add(new PerformancePage(performanceRepository, date, Config.NEIGHBOR));
        performancePages.add(new PerformancePage(performanceRepository, date, Config.REAL));
        return performancePages;
    }
}

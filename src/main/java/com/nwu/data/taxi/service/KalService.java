package com.nwu.data.taxi.service;

import com.nwu.data.taxi.domain.model.GridData;
import com.nwu.data.taxi.domain.model.KalGridProbability;
import com.nwu.data.taxi.domain.repository.GridDataRepository;
import com.nwu.data.taxi.domain.repository.KalGridProbabilityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KalService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProbabilityService probabilityService;
    @Autowired
    private GridDataRepository gridDataRepository;
    @Autowired
    private KalGridProbabilityRepository kalGridProbabilityRepository;

    @Async
    public void calKalProbability(String date, String dayHalfHour){
        List<KalGridProbability> kalGridProbabilities = new ArrayList<>();
        for (GridData gridData : gridDataRepository.findAll()) {
            kalGridProbabilities.addAll(probabilityService.calculateKalGridProbability(date, dayHalfHour, gridData));
            logger.info("processed grid: " + gridData.getGrid());
        }
        kalGridProbabilityRepository.save(kalGridProbabilities);
        logger.info("saved date: " + date + " hour: " + dayHalfHour + "Thread: " +Thread.currentThread().getName());

    }
}

package com.nwu.data.taxi.service.helper.processor;

import com.nwu.data.taxi.domain.model.GridProbability;
import com.nwu.data.taxi.domain.model.GridReading;
import com.nwu.data.taxi.domain.model.Taxi;
import com.nwu.data.taxi.domain.repository.GridProbabilityRepository;
import com.nwu.data.taxi.domain.repository.TaxiRepository;
import com.nwu.data.taxi.service.helper.ProbabilityWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class ProbabilityProcessor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private List<ProbabilityWrapper> probabilityWrappers;
    private TaxiRepository taxiRepository;
    private GridProbabilityRepository gridProbabilityRepository;

    public ProbabilityProcessor(GridProbabilityRepository gridProbabilityRepository, TaxiRepository taxiRepository) {
        this.gridProbabilityRepository = gridProbabilityRepository;
        this.taxiRepository = taxiRepository;

    }

    public void process(Pageable page){
        initProbabilityWrappers();  // 初始化各个wrapper
        gridProbabilityRepository.findAll().forEach(this::processGridProbability);
        gridProbabilityRepository.deleteAll();  // gridProbability中的每个内容,给他们loadProbability
        taxiRepository.findAll(page).forEach(this::processByTaxi);
        save();
    }

    private void processGridProbability(GridProbability gridProbability) {
        probabilityWrappers.forEach(probabilityWrapper -> probabilityWrapper.loadGridProbability(gridProbability));
    }

    private void initProbabilityWrappers() {
        probabilityWrappers = new ArrayList<>();
        probabilityWrappers.add(new ProbabilityWrapper(ProbabilityWrapper.BY_DAY, ProbabilityWrapper.HOUR_CHUNK));
        probabilityWrappers.add(new ProbabilityWrapper(ProbabilityWrapper.BY_WEEK, ProbabilityWrapper.HOUR_CHUNK));
        probabilityWrappers.add(new ProbabilityWrapper(ProbabilityWrapper.BY_YEAR, ProbabilityWrapper.HOUR_CHUNK));
        probabilityWrappers.add(new ProbabilityWrapper(ProbabilityWrapper.BY_WEEK, ProbabilityWrapper.DAY_CHUNK));
        probabilityWrappers.add(new ProbabilityWrapper(ProbabilityWrapper.BY_YEAR, ProbabilityWrapper.DAY_CHUNK));
        probabilityWrappers.add(new ProbabilityWrapper(ProbabilityWrapper.BY_HALF_HOUR, ProbabilityWrapper.HALF_HOUR_CHUNK));
    }

    public void processByTaxi(Taxi taxi) {
        for(GridReading gridReading : taxi.getGridReading()) {
            probabilityWrappers.forEach(probabilityWrapper -> probabilityWrapper.processByTaxi(gridReading));
        }
        logger.info("Taxi: " + taxi.getName() + "is done. id: " + taxi.getId());
    }

    public void save(){
        logger.info("Start saving data.");
        probabilityWrappers.forEach(probabilityWrapper -> saveProbabilities(probabilityWrapper.generateProbabilities(), probabilityWrapper));
        logger.info("Stop saving data.");
    }

    private void saveProbabilities(List<GridProbability> gridProbabilities, ProbabilityWrapper probabilityWrapper) {
        gridProbabilityRepository.save(gridProbabilities);
        logger.info("Saved data for " + probabilityWrapper.getType());
    }
}

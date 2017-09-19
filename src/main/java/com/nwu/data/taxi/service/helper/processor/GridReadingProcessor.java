package com.nwu.data.taxi.service.helper.processor;

import com.nwu.data.taxi.domain.model.GPSData;
import com.nwu.data.taxi.domain.model.GridReading;
import com.nwu.data.taxi.domain.model.Taxi;
import com.nwu.data.taxi.domain.repository.GridReadingRepository;
import com.nwu.data.taxi.domain.repository.TaxiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class GridReadingProcessor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private GridReadingRepository gridReadingRepository;
    private TaxiRepository taxiRepository;
    private List<GridReading> gridReadings;
    private int count;
    private long total;

    public GridReadingProcessor(GridReadingRepository gridReadingRepository, TaxiRepository taxiRepository) {
        this.gridReadingRepository = gridReadingRepository;
        this.taxiRepository = taxiRepository;
    }

    public void process(Pageable page) {
        count += page.getPageNumber() * page.getPageSize();
        total = taxiRepository.count();
        taxiRepository.findAll(page).forEach(taxi -> extractGridReading(taxi));
    }

    private void extractGridReading(Taxi taxi) {
        GPSData last = null;
        GridReading gridReading = null;
        gridReadings = new ArrayList<>();
        for(GPSData current : taxi.getGpsData() ){
            if (null == last) {
                gridReading = new GridReading(current);
                if (current.isOccupied()) {
                    gridReading.setIsPickedUp(true);
                }
                gridReadings.add(gridReading);
            } else {
                if (current.getGrid() != last.getGrid()) {
                    gridReading = new GridReading(current);
                    gridReadings.add(gridReading);
                }
                if (current.isOccupied() && !last.isOccupied()) {
                    gridReading.setIsPickedUp(true);
                }
            }

            last = current;
        }
        count ++;
        gridReadingRepository.save(gridReadings);
        logger.info("Taxi : " + taxi.getName() + ", id: " + taxi.getId() + " is done.(" + count + "/" + total + ")");
    }

}

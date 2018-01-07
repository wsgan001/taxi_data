package com.nwu.data.taxi.service;

import com.nwu.data.taxi.domain.model.GridData;
import com.nwu.data.taxi.domain.model.HalfHourGridProbability;
import com.nwu.data.taxi.domain.model.KalGridProbability;
import com.nwu.data.taxi.domain.repository.HalfHourGridProbabilityRepository;
import com.nwu.data.taxi.domain.repository.KalGridProbabilityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.OptionalDouble;

@Service
public class ProbabilityService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private HalfHourGridProbabilityRepository halfHourGridProbabilityRepository;

    public List<KalGridProbability> calculateKalGridProbability(String date, String dayHalfHour, GridData gridData){
        double wVariance = 0.00001;
        double vVariance = 0.0001;
        List<KalGridProbability> kalGridProbabilities = new ArrayList<>();
        double xHat = -1;
        double pHat;
        double p = 0.3;
        double x = 0;
        double k;
        List<HalfHourGridProbability> halfHourGridProbabilityList = halfHourGridProbabilityRepository.findByGridAndTimeNotContainsAndTimeContainsOrderByTime(gridData.getGrid(), date, dayHalfHour);
        HalfHourGridProbability currentGridProbability = halfHourGridProbabilityRepository.findByGridAndTimeContains(gridData.getGrid(), date+dayHalfHour);
        halfHourGridProbabilityList.add(currentGridProbability);
        for (HalfHourGridProbability halfHourGridProbability : halfHourGridProbabilityList) {
            if (xHat == -1){
                xHat = halfHourGridProbability.getProbability();
                x = halfHourGridProbability.getProbability();
            } else {
                xHat = x;
                pHat = p + wVariance;
                k = pHat/(pHat + vVariance);
                x = xHat + k*(halfHourGridProbability.getProbability() - xHat);
                p = (1-k)*pHat;
            }
        }

        String nextDateTime = addHalfHour(date+dayHalfHour);
        String nextTime = nextDateTime.substring(nextDateTime.length()-4, nextDateTime.length());
        HalfHourGridProbability realGridProbability = halfHourGridProbabilityRepository.findByGridAndTimeContains(gridData.getGrid(), nextDateTime);
        List<HalfHourGridProbability> nextProbabilityList = halfHourGridProbabilityRepository.findByGridAndTimeNotContainsAndTimeContainsOrderByTime(gridData.getGrid(), date, nextTime);
        double avgProbability = 0;
        for (HalfHourGridProbability nextProbability : nextProbabilityList) {
            avgProbability+=nextProbability.getProbability();
        }
        avgProbability /= nextProbabilityList.size();
        KalGridProbability kalGridProbability = new KalGridProbability(currentGridProbability, x, avgProbability, realGridProbability.getProbability());
        kalGridProbabilities.add(kalGridProbability);
        logger.info("processed grid: " + kalGridProbability.getGrid() + "time: " + kalGridProbability.getTime());
        return kalGridProbabilities;
    }

    private String addHalfHour(String date){
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
            return dateFormat.format(new Date(dateFormat.parse(date).getTime() + 1800000));
        } catch (ParseException e) {
            return date;
        }
    }
}

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
        double wVariance = 0.000001;
        double vVariance = 0.00001;
        List<KalGridProbability> kalGridProbabilities = new ArrayList<>();
        double xHat = -1;
        double pHat;
        double p = 0.1;
        double x = 0;
        double k;
        String nextDateTime = addHalfHour(date+dayHalfHour);
        String nextTime = nextDateTime.substring(nextDateTime.length()-4, nextDateTime.length());
        List<HalfHourGridProbability> halfHourGridProbabilityList = halfHourGridProbabilityRepository.findByGridAndTimeNotContainsAndTimeContainsOrderByTime(gridData.getGrid(), date, nextTime);
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

        HalfHourGridProbability realGridProbability = halfHourGridProbabilityRepository.findByGridAndTimeContains(gridData.getGrid(), nextDateTime);
        double avgProbability = getAVGProbability(date+dayHalfHour, gridData.getGrid());
        KalGridProbability kalGridProbability = new KalGridProbability(currentGridProbability, x, avgProbability, realGridProbability.getProbability());
        kalGridProbabilities.add(kalGridProbability);
        logger.info("processed grid: " + kalGridProbability.getGrid() + "time: " + kalGridProbability.getTime());
        return kalGridProbabilities;
    }

    private double getAVGProbability(String currentTime, int grid){
        String previousWeek = previousWeek(currentTime);
        HalfHourGridProbability firstWeek = halfHourGridProbabilityRepository.findByGridAndTimeContains(grid, previousWeek);
        previousWeek = previousWeek(previousWeek);
        HalfHourGridProbability secondWeek = halfHourGridProbabilityRepository.findByGridAndTimeContains(grid, previousWeek);
        return (firstWeek.getProbability()+secondWeek.getProbability())/2;
    }

    private String previousWeek(String date){
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
            return dateFormat.format(new Date(dateFormat.parse(date).getTime() - 604800000));
        } catch (ParseException e) {
            return date;
        }
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

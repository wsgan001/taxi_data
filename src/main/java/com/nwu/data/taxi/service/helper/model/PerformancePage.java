package com.nwu.data.taxi.service.helper.model;

import com.nwu.data.taxi.domain.model.Performance;
import com.nwu.data.taxi.domain.repository.PerformanceRepository;

import java.util.ArrayList;
import java.util.List;

public class PerformancePage {
    private String date;
    private int type;
    private List<PerformanceView> performanceViews;
    private List<String> distancePerformance;
    private List<String> timePerformance;

    public PerformancePage(PerformanceRepository performanceRepository, String date, int type) {
        performanceViews = new ArrayList<>();
        distancePerformance = new ArrayList<>();
        timePerformance = new ArrayList<>();
        this.date = date;
        this.type = type;
        performanceRepository.findByDateAndType(date, type).forEach(this::collectData);
        performanceViews.sort(this::compare);
        performanceViews.forEach(this::calculate);
    }

    private int compare(PerformanceView p1, PerformanceView p2) {
        return p1.getTime().compareTo(p2.getTime());
    }

    private void calculate(PerformanceView performanceView) {
        this.distancePerformance.add(performanceView.getDistancePerformance());
        this.timePerformance.add(performanceView.getTimePerformance());
    }

    private void collectData(Performance performance) {
        for (PerformanceView performanceView : performanceViews){
            if (performanceView.getTime().equals(performance.getTime())){
                performanceView.addPerformance(performance);
                performance = null;
                break;
            }
        }
        if(null != performance){
            performanceViews.add(new PerformanceView(performance));
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<PerformanceView> getPerformanceViews() {
        return performanceViews;
    }

    public void setPerformanceViews(List<PerformanceView> performanceViews) {
        this.performanceViews = performanceViews;
    }

    public List<String> getDistancePerformance() {
        return distancePerformance;
    }

    public void setDistancePerformance(List<String> distancePerformance) {
        this.distancePerformance = distancePerformance;
    }

    public List<String> getTimePerformance() {
        return timePerformance;
    }

    public void setTimePerformance(List<String> timePerformance) {
        this.timePerformance = timePerformance;
    }
}

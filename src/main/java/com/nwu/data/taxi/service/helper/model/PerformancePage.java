package com.nwu.data.taxi.service.helper.model;

import com.nwu.data.taxi.domain.model.Performance;
import com.nwu.data.taxi.domain.repository.PerformanceRepository;
import com.nwu.data.taxi.service.helper.Config;

import java.util.ArrayList;
import java.util.List;

public class PerformancePage {
    private String date;
    private int type;
    private List<PerformanceView> performanceViews;

    public PerformancePage(PerformanceRepository performanceRepository, String date, int type) {
        performanceViews = new ArrayList<>();
        this.date = date;
        this.type = type;
        performanceRepository.findByDateAndType(date, type).forEach(this::calculatePerformance);
        performanceViews.forEach(PerformanceView::calculate);
    }

    private void calculatePerformance(Performance performance) {
        for (PerformanceView performanceView : performanceViews){
            if (performanceView.getTime().equals(performance.getTime())){
                performanceView.addPerformance(performance);
                performance = null;
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
}

package com.nwu.data.taxi.domain.repository;

import com.nwu.data.taxi.domain.model.GridProbability;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GridProbabilityRepository  extends PagingAndSortingRepository<GridProbability, Integer> {
    Iterable<GridProbability> findByTime(String time);
    Iterable<GridProbability> findByTimeTypeAndTimeChunk(int timeType, int timeChunk);
}

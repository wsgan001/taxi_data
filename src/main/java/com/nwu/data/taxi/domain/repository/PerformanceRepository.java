package com.nwu.data.taxi.domain.repository;

import com.nwu.data.taxi.domain.model.Performance;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PerformanceRepository extends  PagingAndSortingRepository<Performance, Integer> {
}

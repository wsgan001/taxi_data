package com.nwu.data.taxi.domain.repository;

import com.nwu.data.taxi.domain.model.GridReading;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GridReadingRepository extends PagingAndSortingRepository<GridReading, Integer> {
}

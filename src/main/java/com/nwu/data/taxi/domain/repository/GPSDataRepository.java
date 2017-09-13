package com.nwu.data.taxi.domain.repository;

import com.nwu.data.taxi.domain.model.GPSData;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GPSDataRepository extends PagingAndSortingRepository<GPSData, Integer> {
    Iterable<GPSData> findByTaxiId(Integer taxiId);
}

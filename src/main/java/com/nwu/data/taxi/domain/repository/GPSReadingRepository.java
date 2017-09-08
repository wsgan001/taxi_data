package com.nwu.data.taxi.domain.repository;

import com.nwu.data.taxi.domain.model.GPSReading;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GPSReadingRepository extends PagingAndSortingRepository<GPSReading, Long> {
}

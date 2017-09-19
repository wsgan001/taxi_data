package com.nwu.data.taxi.domain.repository;

import com.nwu.data.taxi.domain.model.GPSReading;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "gps_reading", path="gps_reading")
public interface GPSReadingRepository extends PagingAndSortingRepository<GPSReading, Integer> {
}

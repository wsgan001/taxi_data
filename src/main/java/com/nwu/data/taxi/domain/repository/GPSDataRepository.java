package com.nwu.data.taxi.domain.repository;

import com.nwu.data.taxi.domain.model.GPSData;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "gps_data", path="gps_data")
public interface GPSDataRepository extends PagingAndSortingRepository<GPSData, Integer> {
}

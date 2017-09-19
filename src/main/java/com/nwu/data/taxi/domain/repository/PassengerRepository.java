package com.nwu.data.taxi.domain.repository;

import com.nwu.data.taxi.domain.model.PassengerData;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "passenger", path="passenger")
public interface PassengerRepository extends PagingAndSortingRepository<PassengerData, Integer> {
    Iterable<PassengerData> findByTravelDate(String travelDate);
}

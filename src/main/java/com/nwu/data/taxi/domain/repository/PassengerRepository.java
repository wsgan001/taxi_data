package com.nwu.data.taxi.domain.repository;

import com.nwu.data.taxi.domain.model.PassengerData;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PassengerRepository extends PagingAndSortingRepository<PassengerData, Integer> {
    Iterable<PassengerData> findByTravelDate(String travelDate);
}

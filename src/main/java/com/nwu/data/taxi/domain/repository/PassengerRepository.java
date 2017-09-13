package com.nwu.data.taxi.domain.repository;

import com.nwu.data.taxi.domain.model.Passenger;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PassengerRepository extends PagingAndSortingRepository<Passenger, Integer> {
}

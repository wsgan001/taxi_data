package com.nwu.data.taxi.domain.repository;

import com.nwu.data.taxi.domain.model.Taxi;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TaxiRepository extends PagingAndSortingRepository<Taxi, Integer> {
}

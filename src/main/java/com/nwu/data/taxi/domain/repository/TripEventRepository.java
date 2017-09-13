package com.nwu.data.taxi.domain.repository;

import com.nwu.data.taxi.domain.model.TripEvent;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TripEventRepository extends PagingAndSortingRepository<TripEvent, Integer> {
    TripEvent findTopByOrderByIdDesc();
}

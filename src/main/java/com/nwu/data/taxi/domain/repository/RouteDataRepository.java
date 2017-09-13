package com.nwu.data.taxi.domain.repository;

import com.nwu.data.taxi.domain.model.RouteData;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RouteDataRepository extends PagingAndSortingRepository<RouteData, Integer> {
}

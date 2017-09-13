package com.nwu.data.taxi.domain.repository;

import com.nwu.data.taxi.domain.model.Route;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RouteRepository extends PagingAndSortingRepository<Route, Integer> {
}

package com.nwu.data.taxi.domain.repository;

import com.nwu.data.taxi.domain.model.Route;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "route", path="route")
public interface RouteRepository extends PagingAndSortingRepository<Route, Integer> {
}

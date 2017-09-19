package com.nwu.data.taxi.domain.repository;

import com.nwu.data.taxi.domain.model.Taxi;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "taxi", path="taxi")
public interface TaxiRepository extends PagingAndSortingRepository<Taxi, Integer> {
    Taxi findTopByName(@Param("name") String name);
}

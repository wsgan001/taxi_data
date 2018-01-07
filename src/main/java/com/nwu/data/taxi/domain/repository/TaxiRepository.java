package com.nwu.data.taxi.domain.repository;

import com.nwu.data.taxi.domain.model.Taxi;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "taxi", path="taxi")
@CacheConfig(cacheNames = "Taxis")
public interface TaxiRepository extends PagingAndSortingRepository<Taxi, Integer> {
    Taxi findTopByName(@Param("name") String name);
    @Cacheable
    Iterable<Taxi> findByNameIn(List<String> names);
}

package com.nwu.data.taxi.domain.repository;

import com.nwu.data.taxi.domain.model.Performance;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "performance", path="performance")
public interface PerformanceRepository extends  PagingAndSortingRepository<Performance, Integer> {
}

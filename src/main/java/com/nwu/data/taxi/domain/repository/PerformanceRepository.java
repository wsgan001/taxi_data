package com.nwu.data.taxi.domain.repository;

import com.nwu.data.taxi.domain.model.Performance;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "performance", path="performance")
public interface PerformanceRepository extends  PagingAndSortingRepository<Performance, Integer> {
    Iterable<Performance> findByDateAndType(String date, int type);
    @Transactional
    Integer removeAllByDateAndType(String date, int type);
}

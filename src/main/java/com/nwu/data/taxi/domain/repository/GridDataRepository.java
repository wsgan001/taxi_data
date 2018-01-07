package com.nwu.data.taxi.domain.repository;

import com.nwu.data.taxi.domain.model.GridData;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "grid_data", path="grid_data")
@CacheConfig(cacheNames = "GridData")
public interface GridDataRepository extends PagingAndSortingRepository<GridData, Integer> {
    @Cacheable
    List<GridData> findByIsSelected(@Param("isSelected")boolean isSelected);
}

package com.nwu.data.taxi.domain.repository;

import com.nwu.data.taxi.domain.model.GridReading;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "grid_reading", path="grid_reading")
public interface GridReadingRepository extends PagingAndSortingRepository<GridReading, Integer> {
}

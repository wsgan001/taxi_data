package com.nwu.data.taxi.domain.repository;

import com.nwu.data.taxi.domain.model.KalGridProbability;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "kal_grid_probability", path="kal_grid_probability")
public interface KalGridProbabilityRepository extends PagingAndSortingRepository<KalGridProbability, Integer> {
    Iterable<KalGridProbability> findByTime(@Param("time") String time);
    List<KalGridProbability> findByTimeContains(@Param("time") String date);
}

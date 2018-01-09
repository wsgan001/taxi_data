package com.nwu.data.taxi.domain.repository;

import com.nwu.data.taxi.domain.model.HalfHourGridProbability;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "half_hour_grid_probability", path="half_hour_grid_probability")
public interface HalfHourGridProbabilityRepository extends PagingAndSortingRepository<HalfHourGridProbability, Integer> {
    Iterable<HalfHourGridProbability> findByTime(@Param("time") String time);
    HalfHourGridProbability findByGridAndTimeContains(@Param("grid") int grid, @Param("time") String time);
    List<HalfHourGridProbability> findByGridAndTimeNotContainsAndTimeContainsOrderByTime(@Param("grid") int grid, @Param("date") String date, @Param("time") String time);
}

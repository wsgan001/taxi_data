package com.nwu.data.taxi.domain.repository;

import com.nwu.data.taxi.domain.model.GridProbability;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "grid_probability", path="grid_probability")
public interface GridProbabilityRepository  extends PagingAndSortingRepository<GridProbability, Integer> {
    Iterable<GridProbability> findByTime(@Param("time") String time);
    Iterable<GridProbability> findByTimeTypeAndTimeChunk(@Param("timeType") int timeType, @Param("timeChunk") int timeChunk);

    Iterable<GridProbability> findByTimeStartingWithAndTimeChunk(@Param("time") String date, @Param("timeChunk") int halfHourChunk);
}

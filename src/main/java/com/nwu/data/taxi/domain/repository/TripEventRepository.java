package com.nwu.data.taxi.domain.repository;

import com.nwu.data.taxi.domain.model.TripEvent;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "trip_event", path="trip_event")
public interface TripEventRepository extends PagingAndSortingRepository<TripEvent, Integer> {
    TripEvent findTopByOrderByIdDesc();
    Iterable<TripEvent> findByTaxiNameAndEventDate(@Param("taxiName") String taxiName, @Param("eventDate") String  eventDate);

}

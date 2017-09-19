package com.nwu.data.taxi.domain.repository;

import com.nwu.data.taxi.domain.model.TripEvent;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TripEventRepository extends PagingAndSortingRepository<TripEvent, Integer> {
    TripEvent findTopByOrderByIdDesc();
    TripEvent findTopByTaxiNameAndEventTypeAndEventDate(String taxiName, int eventType, String eventDate);
    Iterable<TripEvent> findByTaxiNameAndEventTypeAndEventDate(String taxiName, int eventType, String eventDate);
    Iterable<TripEvent> findByTaxiNameAndEventDate(String taxiName, String eventDate);

}

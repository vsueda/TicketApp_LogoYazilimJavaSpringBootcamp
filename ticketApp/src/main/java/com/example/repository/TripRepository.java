package com.example.repository;

import com.example.dto.TripDTO;
import com.example.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    @Query("SELECT t FROM Trip t WHERE t.tripFrom = ?1 or t.tripTo = ?2 or t.timeTripFrom = ?3 or " +
            "t.timeTripTo = ?4 or t.busId = ?5 or t.planeId = ?6")
    List<Trip> findTripByTripFromOrTripToOrTimeTripFromOrTimeTripToOrBusIdOrPlaneId(String tripFrom,
                      String tripTo, LocalDateTime timeTripFrom, LocalDateTime timeTripTo, Long busId, Long planeId);
}

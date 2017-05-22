package com.weareholidays.repository;

import com.weareholidays.domain.Trip;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Trip entity.
 */
@SuppressWarnings("unused")
public interface TripRepository extends JpaRepository<Trip,Long> {

    @Query("select trip from Trip trip where trip.user.login = ?#{principal.username}")
    List<Trip> findByUserIsCurrentUser();

    @Query("select trip from Trip trip where trip.user.login = ?#{principal.username} and trip.isPublished = true and trip.isDeleted = false")
    List<Trip> getPublishedTripsOfUser();

    @Query("select trip from Trip trip where trip.isPublished = true and trip.isDeleted = false")
    List<Trip> getAllPublishedTrips(Pageable pageable);

}

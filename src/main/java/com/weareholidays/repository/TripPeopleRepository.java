package com.weareholidays.repository;

import com.weareholidays.domain.TripPeople;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TripPeople entity.
 */
@SuppressWarnings("unused")
public interface TripPeopleRepository extends JpaRepository<TripPeople,Long> {

}

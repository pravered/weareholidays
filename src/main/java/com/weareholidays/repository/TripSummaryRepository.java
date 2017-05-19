package com.weareholidays.repository;

import com.weareholidays.domain.TripSummary;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TripSummary entity.
 */
@SuppressWarnings("unused")
public interface TripSummaryRepository extends JpaRepository<TripSummary,Long> {

}

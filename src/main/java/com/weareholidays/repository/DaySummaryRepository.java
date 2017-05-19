package com.weareholidays.repository;

import com.weareholidays.domain.DaySummary;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DaySummary entity.
 */
@SuppressWarnings("unused")
public interface DaySummaryRepository extends JpaRepository<DaySummary,Long> {

}

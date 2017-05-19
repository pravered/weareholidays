package com.weareholidays.repository;

import com.weareholidays.domain.Day;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Day entity.
 */
@SuppressWarnings("unused")
public interface DayRepository extends JpaRepository<Day,Long> {

}

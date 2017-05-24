package com.weareholidays.repository;

import com.weareholidays.domain.CheckIn;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CheckIn entity.
 */
@SuppressWarnings("unused")
public interface CheckInRepository extends JpaRepository<CheckIn,Long> {

}

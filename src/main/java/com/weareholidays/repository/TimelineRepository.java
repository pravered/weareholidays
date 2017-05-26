package com.weareholidays.repository;

import com.weareholidays.domain.Timeline;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Timeline entity.
 */
@SuppressWarnings("unused")
public interface TimelineRepository extends JpaRepository<Timeline,Long> {

}

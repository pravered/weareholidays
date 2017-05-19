package com.weareholidays.repository;

import com.weareholidays.domain.TripSettings;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TripSettings entity.
 */
@SuppressWarnings("unused")
public interface TripSettingsRepository extends JpaRepository<TripSettings,Long> {

}

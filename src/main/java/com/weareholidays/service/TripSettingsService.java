package com.weareholidays.service;

import com.weareholidays.service.dto.TripSettingsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing TripSettings.
 */
public interface TripSettingsService {

    /**
     * Save a tripSettings.
     *
     * @param tripSettingsDTO the entity to save
     * @return the persisted entity
     */
    TripSettingsDTO save(TripSettingsDTO tripSettingsDTO);

    /**
     *  Get all the tripSettings.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TripSettingsDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" tripSettings.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TripSettingsDTO findOne(Long id);

    /**
     *  Delete the "id" tripSettings.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

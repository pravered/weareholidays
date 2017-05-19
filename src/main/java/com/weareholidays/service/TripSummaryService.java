package com.weareholidays.service;

import com.weareholidays.service.dto.TripSummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing TripSummary.
 */
public interface TripSummaryService {

    /**
     * Save a tripSummary.
     *
     * @param tripSummaryDTO the entity to save
     * @return the persisted entity
     */
    TripSummaryDTO save(TripSummaryDTO tripSummaryDTO);

    /**
     *  Get all the tripSummaries.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TripSummaryDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" tripSummary.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TripSummaryDTO findOne(Long id);

    /**
     *  Delete the "id" tripSummary.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

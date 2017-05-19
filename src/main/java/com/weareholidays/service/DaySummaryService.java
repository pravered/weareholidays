package com.weareholidays.service;

import com.weareholidays.service.dto.DaySummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing DaySummary.
 */
public interface DaySummaryService {

    /**
     * Save a daySummary.
     *
     * @param daySummaryDTO the entity to save
     * @return the persisted entity
     */
    DaySummaryDTO save(DaySummaryDTO daySummaryDTO);

    /**
     *  Get all the daySummaries.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DaySummaryDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" daySummary.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DaySummaryDTO findOne(Long id);

    /**
     *  Delete the "id" daySummary.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

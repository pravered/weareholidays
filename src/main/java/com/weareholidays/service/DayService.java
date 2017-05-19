package com.weareholidays.service;

import com.weareholidays.service.dto.DayDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Day.
 */
public interface DayService {

    /**
     * Save a day.
     *
     * @param dayDTO the entity to save
     * @return the persisted entity
     */
    DayDTO save(DayDTO dayDTO);

    /**
     *  Get all the days.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DayDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" day.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DayDTO findOne(Long id);

    /**
     *  Delete the "id" day.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

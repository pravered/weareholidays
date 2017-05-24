package com.weareholidays.service;

import com.weareholidays.service.dto.CheckInDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing CheckIn.
 */
public interface CheckInService {

    /**
     * Save a checkIn.
     *
     * @param checkInDTO the entity to save
     * @return the persisted entity
     */
    CheckInDTO save(CheckInDTO checkInDTO);

    /**
     *  Get all the checkIns.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CheckInDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" checkIn.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CheckInDTO findOne(Long id);

    /**
     *  Delete the "id" checkIn.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

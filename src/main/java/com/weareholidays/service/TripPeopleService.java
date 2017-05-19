package com.weareholidays.service;

import com.weareholidays.service.dto.TripPeopleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing TripPeople.
 */
public interface TripPeopleService {

    /**
     * Save a tripPeople.
     *
     * @param tripPeopleDTO the entity to save
     * @return the persisted entity
     */
    TripPeopleDTO save(TripPeopleDTO tripPeopleDTO);

    /**
     *  Get all the tripPeople.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TripPeopleDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" tripPeople.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TripPeopleDTO findOne(Long id);

    /**
     *  Delete the "id" tripPeople.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

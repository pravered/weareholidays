package com.weareholidays.service.impl;

import com.weareholidays.service.TripPeopleService;
import com.weareholidays.domain.TripPeople;
import com.weareholidays.repository.TripPeopleRepository;
import com.weareholidays.service.dto.TripPeopleDTO;
import com.weareholidays.service.mapper.TripPeopleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing TripPeople.
 */
@Service
@Transactional
public class TripPeopleServiceImpl implements TripPeopleService{

    private final Logger log = LoggerFactory.getLogger(TripPeopleServiceImpl.class);
    
    private final TripPeopleRepository tripPeopleRepository;

    private final TripPeopleMapper tripPeopleMapper;

    public TripPeopleServiceImpl(TripPeopleRepository tripPeopleRepository, TripPeopleMapper tripPeopleMapper) {
        this.tripPeopleRepository = tripPeopleRepository;
        this.tripPeopleMapper = tripPeopleMapper;
    }

    /**
     * Save a tripPeople.
     *
     * @param tripPeopleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TripPeopleDTO save(TripPeopleDTO tripPeopleDTO) {
        log.debug("Request to save TripPeople : {}", tripPeopleDTO);
        TripPeople tripPeople = tripPeopleMapper.tripPeopleDTOToTripPeople(tripPeopleDTO);
        tripPeople = tripPeopleRepository.save(tripPeople);
        TripPeopleDTO result = tripPeopleMapper.tripPeopleToTripPeopleDTO(tripPeople);
        return result;
    }

    /**
     *  Get all the tripPeople.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TripPeopleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TripPeople");
        Page<TripPeople> result = tripPeopleRepository.findAll(pageable);
        return result.map(tripPeople -> tripPeopleMapper.tripPeopleToTripPeopleDTO(tripPeople));
    }

    /**
     *  Get one tripPeople by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TripPeopleDTO findOne(Long id) {
        log.debug("Request to get TripPeople : {}", id);
        TripPeople tripPeople = tripPeopleRepository.findOne(id);
        TripPeopleDTO tripPeopleDTO = tripPeopleMapper.tripPeopleToTripPeopleDTO(tripPeople);
        return tripPeopleDTO;
    }

    /**
     *  Delete the  tripPeople by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TripPeople : {}", id);
        tripPeopleRepository.delete(id);
    }
}

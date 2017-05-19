package com.weareholidays.service.impl;

import com.weareholidays.service.TripSummaryService;
import com.weareholidays.domain.TripSummary;
import com.weareholidays.repository.TripSummaryRepository;
import com.weareholidays.service.dto.TripSummaryDTO;
import com.weareholidays.service.mapper.TripSummaryMapper;
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
 * Service Implementation for managing TripSummary.
 */
@Service
@Transactional
public class TripSummaryServiceImpl implements TripSummaryService{

    private final Logger log = LoggerFactory.getLogger(TripSummaryServiceImpl.class);
    
    private final TripSummaryRepository tripSummaryRepository;

    private final TripSummaryMapper tripSummaryMapper;

    public TripSummaryServiceImpl(TripSummaryRepository tripSummaryRepository, TripSummaryMapper tripSummaryMapper) {
        this.tripSummaryRepository = tripSummaryRepository;
        this.tripSummaryMapper = tripSummaryMapper;
    }

    /**
     * Save a tripSummary.
     *
     * @param tripSummaryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TripSummaryDTO save(TripSummaryDTO tripSummaryDTO) {
        log.debug("Request to save TripSummary : {}", tripSummaryDTO);
        TripSummary tripSummary = tripSummaryMapper.tripSummaryDTOToTripSummary(tripSummaryDTO);
        tripSummary = tripSummaryRepository.save(tripSummary);
        TripSummaryDTO result = tripSummaryMapper.tripSummaryToTripSummaryDTO(tripSummary);
        return result;
    }

    /**
     *  Get all the tripSummaries.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TripSummaryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TripSummaries");
        Page<TripSummary> result = tripSummaryRepository.findAll(pageable);
        return result.map(tripSummary -> tripSummaryMapper.tripSummaryToTripSummaryDTO(tripSummary));
    }

    /**
     *  Get one tripSummary by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TripSummaryDTO findOne(Long id) {
        log.debug("Request to get TripSummary : {}", id);
        TripSummary tripSummary = tripSummaryRepository.findOne(id);
        TripSummaryDTO tripSummaryDTO = tripSummaryMapper.tripSummaryToTripSummaryDTO(tripSummary);
        return tripSummaryDTO;
    }

    /**
     *  Delete the  tripSummary by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TripSummary : {}", id);
        tripSummaryRepository.delete(id);
    }
}

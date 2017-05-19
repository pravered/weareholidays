package com.weareholidays.service.impl;

import com.weareholidays.service.DaySummaryService;
import com.weareholidays.domain.DaySummary;
import com.weareholidays.repository.DaySummaryRepository;
import com.weareholidays.service.dto.DaySummaryDTO;
import com.weareholidays.service.mapper.DaySummaryMapper;
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
 * Service Implementation for managing DaySummary.
 */
@Service
@Transactional
public class DaySummaryServiceImpl implements DaySummaryService{

    private final Logger log = LoggerFactory.getLogger(DaySummaryServiceImpl.class);
    
    private final DaySummaryRepository daySummaryRepository;

    private final DaySummaryMapper daySummaryMapper;

    public DaySummaryServiceImpl(DaySummaryRepository daySummaryRepository, DaySummaryMapper daySummaryMapper) {
        this.daySummaryRepository = daySummaryRepository;
        this.daySummaryMapper = daySummaryMapper;
    }

    /**
     * Save a daySummary.
     *
     * @param daySummaryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DaySummaryDTO save(DaySummaryDTO daySummaryDTO) {
        log.debug("Request to save DaySummary : {}", daySummaryDTO);
        DaySummary daySummary = daySummaryMapper.daySummaryDTOToDaySummary(daySummaryDTO);
        daySummary = daySummaryRepository.save(daySummary);
        DaySummaryDTO result = daySummaryMapper.daySummaryToDaySummaryDTO(daySummary);
        return result;
    }

    /**
     *  Get all the daySummaries.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DaySummaryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DaySummaries");
        Page<DaySummary> result = daySummaryRepository.findAll(pageable);
        return result.map(daySummary -> daySummaryMapper.daySummaryToDaySummaryDTO(daySummary));
    }

    /**
     *  Get one daySummary by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DaySummaryDTO findOne(Long id) {
        log.debug("Request to get DaySummary : {}", id);
        DaySummary daySummary = daySummaryRepository.findOne(id);
        DaySummaryDTO daySummaryDTO = daySummaryMapper.daySummaryToDaySummaryDTO(daySummary);
        return daySummaryDTO;
    }

    /**
     *  Delete the  daySummary by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DaySummary : {}", id);
        daySummaryRepository.delete(id);
    }
}

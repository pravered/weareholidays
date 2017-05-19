package com.weareholidays.service.impl;

import com.weareholidays.service.TripSettingsService;
import com.weareholidays.domain.TripSettings;
import com.weareholidays.repository.TripSettingsRepository;
import com.weareholidays.service.dto.TripSettingsDTO;
import com.weareholidays.service.mapper.TripSettingsMapper;
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
 * Service Implementation for managing TripSettings.
 */
@Service
@Transactional
public class TripSettingsServiceImpl implements TripSettingsService{

    private final Logger log = LoggerFactory.getLogger(TripSettingsServiceImpl.class);
    
    private final TripSettingsRepository tripSettingsRepository;

    private final TripSettingsMapper tripSettingsMapper;

    public TripSettingsServiceImpl(TripSettingsRepository tripSettingsRepository, TripSettingsMapper tripSettingsMapper) {
        this.tripSettingsRepository = tripSettingsRepository;
        this.tripSettingsMapper = tripSettingsMapper;
    }

    /**
     * Save a tripSettings.
     *
     * @param tripSettingsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TripSettingsDTO save(TripSettingsDTO tripSettingsDTO) {
        log.debug("Request to save TripSettings : {}", tripSettingsDTO);
        TripSettings tripSettings = tripSettingsMapper.tripSettingsDTOToTripSettings(tripSettingsDTO);
        tripSettings = tripSettingsRepository.save(tripSettings);
        TripSettingsDTO result = tripSettingsMapper.tripSettingsToTripSettingsDTO(tripSettings);
        return result;
    }

    /**
     *  Get all the tripSettings.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TripSettingsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TripSettings");
        Page<TripSettings> result = tripSettingsRepository.findAll(pageable);
        return result.map(tripSettings -> tripSettingsMapper.tripSettingsToTripSettingsDTO(tripSettings));
    }

    /**
     *  Get one tripSettings by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TripSettingsDTO findOne(Long id) {
        log.debug("Request to get TripSettings : {}", id);
        TripSettings tripSettings = tripSettingsRepository.findOne(id);
        TripSettingsDTO tripSettingsDTO = tripSettingsMapper.tripSettingsToTripSettingsDTO(tripSettings);
        return tripSettingsDTO;
    }

    /**
     *  Delete the  tripSettings by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TripSettings : {}", id);
        tripSettingsRepository.delete(id);
    }
}

package com.weareholidays.service.impl;

import com.weareholidays.service.DayService;
import com.weareholidays.domain.Day;
import com.weareholidays.repository.DayRepository;
import com.weareholidays.service.dto.DayDTO;
import com.weareholidays.service.mapper.DayMapper;
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
 * Service Implementation for managing Day.
 */
@Service
@Transactional
public class DayServiceImpl implements DayService{

    private final Logger log = LoggerFactory.getLogger(DayServiceImpl.class);
    
    private final DayRepository dayRepository;

    private final DayMapper dayMapper;

    public DayServiceImpl(DayRepository dayRepository, DayMapper dayMapper) {
        this.dayRepository = dayRepository;
        this.dayMapper = dayMapper;
    }

    /**
     * Save a day.
     *
     * @param dayDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DayDTO save(DayDTO dayDTO) {
        log.debug("Request to save Day : {}", dayDTO);
        Day day = dayMapper.dayDTOToDay(dayDTO);
        day = dayRepository.save(day);
        DayDTO result = dayMapper.dayToDayDTO(day);
        return result;
    }

    /**
     *  Get all the days.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DayDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Days");
        Page<Day> result = dayRepository.findAll(pageable);
        return result.map(day -> dayMapper.dayToDayDTO(day));
    }

    /**
     *  Get one day by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DayDTO findOne(Long id) {
        log.debug("Request to get Day : {}", id);
        Day day = dayRepository.findOne(id);
        DayDTO dayDTO = dayMapper.dayToDayDTO(day);
        return dayDTO;
    }

    /**
     *  Delete the  day by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Day : {}", id);
        dayRepository.delete(id);
    }
}

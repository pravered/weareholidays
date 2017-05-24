package com.weareholidays.service.impl;

import com.weareholidays.service.CheckInService;
import com.weareholidays.domain.CheckIn;
import com.weareholidays.repository.CheckInRepository;
import com.weareholidays.service.dto.CheckInDTO;
import com.weareholidays.service.mapper.CheckInMapper;
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
 * Service Implementation for managing CheckIn.
 */
@Service
@Transactional
public class CheckInServiceImpl implements CheckInService{

    private final Logger log = LoggerFactory.getLogger(CheckInServiceImpl.class);
    
    private final CheckInRepository checkInRepository;

    private final CheckInMapper checkInMapper;

    public CheckInServiceImpl(CheckInRepository checkInRepository, CheckInMapper checkInMapper) {
        this.checkInRepository = checkInRepository;
        this.checkInMapper = checkInMapper;
    }

    /**
     * Save a checkIn.
     *
     * @param checkInDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CheckInDTO save(CheckInDTO checkInDTO) {
        log.debug("Request to save CheckIn : {}", checkInDTO);
        CheckIn checkIn = checkInMapper.checkInDTOToCheckIn(checkInDTO);
        checkIn = checkInRepository.save(checkIn);
        CheckInDTO result = checkInMapper.checkInToCheckInDTO(checkIn);
        return result;
    }

    /**
     *  Get all the checkIns.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CheckInDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CheckIns");
        Page<CheckIn> result = checkInRepository.findAll(pageable);
        return result.map(checkIn -> checkInMapper.checkInToCheckInDTO(checkIn));
    }

    /**
     *  Get one checkIn by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CheckInDTO findOne(Long id) {
        log.debug("Request to get CheckIn : {}", id);
        CheckIn checkIn = checkInRepository.findOne(id);
        CheckInDTO checkInDTO = checkInMapper.checkInToCheckInDTO(checkIn);
        return checkInDTO;
    }

    /**
     *  Delete the  checkIn by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CheckIn : {}", id);
        checkInRepository.delete(id);
    }
}

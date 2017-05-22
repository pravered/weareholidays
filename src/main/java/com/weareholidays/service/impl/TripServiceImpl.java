package com.weareholidays.service.impl;

import com.weareholidays.service.TripService;
import com.weareholidays.domain.Trip;
import com.weareholidays.repository.TripRepository;
import com.weareholidays.service.dto.TripDTO;
import com.weareholidays.service.mapper.TripMapper;
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
 * Service Implementation for managing Trip.
 */
@Service
@Transactional
public class TripServiceImpl implements TripService{

    private final Logger log = LoggerFactory.getLogger(TripServiceImpl.class);

    private final TripRepository tripRepository;

    private final TripMapper tripMapper;

    public TripServiceImpl(TripRepository tripRepository, TripMapper tripMapper) {
        this.tripRepository = tripRepository;
        this.tripMapper = tripMapper;
    }

    @Override
    public List<TripDTO> getPublishedTripsForUser() {
        return tripMapper.tripsToTripDTOs(tripRepository.getPublishedTripsOfUser());
    }

    @Override
    public Page<TripDTO> getAllPublishedTrips(Pageable pageable) {

        Page<Trip> result = tripRepository.findAll(pageable);
        return result.map(trip -> tripMapper.tripToTripDTO(trip));
//        return tripMapper.tripsToTripDTOs(tripRepository.getAllPublishedTrips());
    }

    /**
     * Save a trip.
     *
     * @param tripDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TripDTO save(TripDTO tripDTO) {
        log.debug("Request to save Trip : {}", tripDTO);
        Trip trip = tripMapper.tripDTOToTrip(tripDTO);
        trip = tripRepository.save(trip);
        TripDTO result = tripMapper.tripToTripDTO(trip);
        return result;
    }

    /**
     *  Get all the trips.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TripDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Trips");
        Page<Trip> result = tripRepository.findAll(pageable);
        return result.map(trip -> tripMapper.tripToTripDTO(trip));
    }

    /**
     *  Get one trip by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TripDTO findOne(Long id) {
        log.debug("Request to get Trip : {}", id);
        Trip trip = tripRepository.findOne(id);
        TripDTO tripDTO = tripMapper.tripToTripDTO(trip);
        return tripDTO;
    }

    /**
     *  Delete the  trip by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Trip : {}", id);
        tripRepository.delete(id);
    }
}

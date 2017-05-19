package com.weareholidays.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.weareholidays.service.TripPeopleService;
import com.weareholidays.web.rest.util.HeaderUtil;
import com.weareholidays.web.rest.util.PaginationUtil;
import com.weareholidays.service.dto.TripPeopleDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing TripPeople.
 */
@RestController
@RequestMapping("/api")
public class TripPeopleResource {

    private final Logger log = LoggerFactory.getLogger(TripPeopleResource.class);

    private static final String ENTITY_NAME = "tripPeople";
        
    private final TripPeopleService tripPeopleService;

    public TripPeopleResource(TripPeopleService tripPeopleService) {
        this.tripPeopleService = tripPeopleService;
    }

    /**
     * POST  /trip-people : Create a new tripPeople.
     *
     * @param tripPeopleDTO the tripPeopleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tripPeopleDTO, or with status 400 (Bad Request) if the tripPeople has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/trip-people")
    @Timed
    public ResponseEntity<TripPeopleDTO> createTripPeople(@RequestBody TripPeopleDTO tripPeopleDTO) throws URISyntaxException {
        log.debug("REST request to save TripPeople : {}", tripPeopleDTO);
        if (tripPeopleDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tripPeople cannot already have an ID")).body(null);
        }
        TripPeopleDTO result = tripPeopleService.save(tripPeopleDTO);
        return ResponseEntity.created(new URI("/api/trip-people/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /trip-people : Updates an existing tripPeople.
     *
     * @param tripPeopleDTO the tripPeopleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tripPeopleDTO,
     * or with status 400 (Bad Request) if the tripPeopleDTO is not valid,
     * or with status 500 (Internal Server Error) if the tripPeopleDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/trip-people")
    @Timed
    public ResponseEntity<TripPeopleDTO> updateTripPeople(@RequestBody TripPeopleDTO tripPeopleDTO) throws URISyntaxException {
        log.debug("REST request to update TripPeople : {}", tripPeopleDTO);
        if (tripPeopleDTO.getId() == null) {
            return createTripPeople(tripPeopleDTO);
        }
        TripPeopleDTO result = tripPeopleService.save(tripPeopleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tripPeopleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /trip-people : get all the tripPeople.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tripPeople in body
     */
    @GetMapping("/trip-people")
    @Timed
    public ResponseEntity<List<TripPeopleDTO>> getAllTripPeople(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of TripPeople");
        Page<TripPeopleDTO> page = tripPeopleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/trip-people");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /trip-people/:id : get the "id" tripPeople.
     *
     * @param id the id of the tripPeopleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tripPeopleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/trip-people/{id}")
    @Timed
    public ResponseEntity<TripPeopleDTO> getTripPeople(@PathVariable Long id) {
        log.debug("REST request to get TripPeople : {}", id);
        TripPeopleDTO tripPeopleDTO = tripPeopleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tripPeopleDTO));
    }

    /**
     * DELETE  /trip-people/:id : delete the "id" tripPeople.
     *
     * @param id the id of the tripPeopleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/trip-people/{id}")
    @Timed
    public ResponseEntity<Void> deleteTripPeople(@PathVariable Long id) {
        log.debug("REST request to delete TripPeople : {}", id);
        tripPeopleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}

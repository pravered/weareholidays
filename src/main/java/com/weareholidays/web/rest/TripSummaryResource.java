package com.weareholidays.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.weareholidays.service.TripSummaryService;
import com.weareholidays.web.rest.util.HeaderUtil;
import com.weareholidays.web.rest.util.PaginationUtil;
import com.weareholidays.service.dto.TripSummaryDTO;
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
 * REST controller for managing TripSummary.
 */
@RestController
@RequestMapping("/api")
public class TripSummaryResource {

    private final Logger log = LoggerFactory.getLogger(TripSummaryResource.class);

    private static final String ENTITY_NAME = "tripSummary";
        
    private final TripSummaryService tripSummaryService;

    public TripSummaryResource(TripSummaryService tripSummaryService) {
        this.tripSummaryService = tripSummaryService;
    }

    /**
     * POST  /trip-summaries : Create a new tripSummary.
     *
     * @param tripSummaryDTO the tripSummaryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tripSummaryDTO, or with status 400 (Bad Request) if the tripSummary has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/trip-summaries")
    @Timed
    public ResponseEntity<TripSummaryDTO> createTripSummary(@RequestBody TripSummaryDTO tripSummaryDTO) throws URISyntaxException {
        log.debug("REST request to save TripSummary : {}", tripSummaryDTO);
        if (tripSummaryDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tripSummary cannot already have an ID")).body(null);
        }
        TripSummaryDTO result = tripSummaryService.save(tripSummaryDTO);
        return ResponseEntity.created(new URI("/api/trip-summaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /trip-summaries : Updates an existing tripSummary.
     *
     * @param tripSummaryDTO the tripSummaryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tripSummaryDTO,
     * or with status 400 (Bad Request) if the tripSummaryDTO is not valid,
     * or with status 500 (Internal Server Error) if the tripSummaryDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/trip-summaries")
    @Timed
    public ResponseEntity<TripSummaryDTO> updateTripSummary(@RequestBody TripSummaryDTO tripSummaryDTO) throws URISyntaxException {
        log.debug("REST request to update TripSummary : {}", tripSummaryDTO);
        if (tripSummaryDTO.getId() == null) {
            return createTripSummary(tripSummaryDTO);
        }
        TripSummaryDTO result = tripSummaryService.save(tripSummaryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tripSummaryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /trip-summaries : get all the tripSummaries.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tripSummaries in body
     */
    @GetMapping("/trip-summaries")
    @Timed
    public ResponseEntity<List<TripSummaryDTO>> getAllTripSummaries(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of TripSummaries");
        Page<TripSummaryDTO> page = tripSummaryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/trip-summaries");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /trip-summaries/:id : get the "id" tripSummary.
     *
     * @param id the id of the tripSummaryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tripSummaryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/trip-summaries/{id}")
    @Timed
    public ResponseEntity<TripSummaryDTO> getTripSummary(@PathVariable Long id) {
        log.debug("REST request to get TripSummary : {}", id);
        TripSummaryDTO tripSummaryDTO = tripSummaryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tripSummaryDTO));
    }

    /**
     * DELETE  /trip-summaries/:id : delete the "id" tripSummary.
     *
     * @param id the id of the tripSummaryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/trip-summaries/{id}")
    @Timed
    public ResponseEntity<Void> deleteTripSummary(@PathVariable Long id) {
        log.debug("REST request to delete TripSummary : {}", id);
        tripSummaryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}

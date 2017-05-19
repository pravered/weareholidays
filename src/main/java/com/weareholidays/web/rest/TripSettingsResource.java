package com.weareholidays.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.weareholidays.service.TripSettingsService;
import com.weareholidays.web.rest.util.HeaderUtil;
import com.weareholidays.web.rest.util.PaginationUtil;
import com.weareholidays.service.dto.TripSettingsDTO;
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
 * REST controller for managing TripSettings.
 */
@RestController
@RequestMapping("/api")
public class TripSettingsResource {

    private final Logger log = LoggerFactory.getLogger(TripSettingsResource.class);

    private static final String ENTITY_NAME = "tripSettings";
        
    private final TripSettingsService tripSettingsService;

    public TripSettingsResource(TripSettingsService tripSettingsService) {
        this.tripSettingsService = tripSettingsService;
    }

    /**
     * POST  /trip-settings : Create a new tripSettings.
     *
     * @param tripSettingsDTO the tripSettingsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tripSettingsDTO, or with status 400 (Bad Request) if the tripSettings has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/trip-settings")
    @Timed
    public ResponseEntity<TripSettingsDTO> createTripSettings(@RequestBody TripSettingsDTO tripSettingsDTO) throws URISyntaxException {
        log.debug("REST request to save TripSettings : {}", tripSettingsDTO);
        if (tripSettingsDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tripSettings cannot already have an ID")).body(null);
        }
        TripSettingsDTO result = tripSettingsService.save(tripSettingsDTO);
        return ResponseEntity.created(new URI("/api/trip-settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /trip-settings : Updates an existing tripSettings.
     *
     * @param tripSettingsDTO the tripSettingsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tripSettingsDTO,
     * or with status 400 (Bad Request) if the tripSettingsDTO is not valid,
     * or with status 500 (Internal Server Error) if the tripSettingsDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/trip-settings")
    @Timed
    public ResponseEntity<TripSettingsDTO> updateTripSettings(@RequestBody TripSettingsDTO tripSettingsDTO) throws URISyntaxException {
        log.debug("REST request to update TripSettings : {}", tripSettingsDTO);
        if (tripSettingsDTO.getId() == null) {
            return createTripSettings(tripSettingsDTO);
        }
        TripSettingsDTO result = tripSettingsService.save(tripSettingsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tripSettingsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /trip-settings : get all the tripSettings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tripSettings in body
     */
    @GetMapping("/trip-settings")
    @Timed
    public ResponseEntity<List<TripSettingsDTO>> getAllTripSettings(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of TripSettings");
        Page<TripSettingsDTO> page = tripSettingsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/trip-settings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /trip-settings/:id : get the "id" tripSettings.
     *
     * @param id the id of the tripSettingsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tripSettingsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/trip-settings/{id}")
    @Timed
    public ResponseEntity<TripSettingsDTO> getTripSettings(@PathVariable Long id) {
        log.debug("REST request to get TripSettings : {}", id);
        TripSettingsDTO tripSettingsDTO = tripSettingsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tripSettingsDTO));
    }

    /**
     * DELETE  /trip-settings/:id : delete the "id" tripSettings.
     *
     * @param id the id of the tripSettingsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/trip-settings/{id}")
    @Timed
    public ResponseEntity<Void> deleteTripSettings(@PathVariable Long id) {
        log.debug("REST request to delete TripSettings : {}", id);
        tripSettingsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}

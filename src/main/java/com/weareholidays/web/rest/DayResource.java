package com.weareholidays.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.weareholidays.service.DayService;
import com.weareholidays.web.rest.util.HeaderUtil;
import com.weareholidays.web.rest.util.PaginationUtil;
import com.weareholidays.service.dto.DayDTO;
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
 * REST controller for managing Day.
 */
@RestController
@RequestMapping("/api")
public class DayResource {

    private final Logger log = LoggerFactory.getLogger(DayResource.class);

    private static final String ENTITY_NAME = "day";
        
    private final DayService dayService;

    public DayResource(DayService dayService) {
        this.dayService = dayService;
    }

    /**
     * POST  /days : Create a new day.
     *
     * @param dayDTO the dayDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dayDTO, or with status 400 (Bad Request) if the day has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/days")
    @Timed
    public ResponseEntity<DayDTO> createDay(@RequestBody DayDTO dayDTO) throws URISyntaxException {
        log.debug("REST request to save Day : {}", dayDTO);
        if (dayDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new day cannot already have an ID")).body(null);
        }
        DayDTO result = dayService.save(dayDTO);
        return ResponseEntity.created(new URI("/api/days/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /days : Updates an existing day.
     *
     * @param dayDTO the dayDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dayDTO,
     * or with status 400 (Bad Request) if the dayDTO is not valid,
     * or with status 500 (Internal Server Error) if the dayDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/days")
    @Timed
    public ResponseEntity<DayDTO> updateDay(@RequestBody DayDTO dayDTO) throws URISyntaxException {
        log.debug("REST request to update Day : {}", dayDTO);
        if (dayDTO.getId() == null) {
            return createDay(dayDTO);
        }
        DayDTO result = dayService.save(dayDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dayDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /days : get all the days.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of days in body
     */
    @GetMapping("/days")
    @Timed
    public ResponseEntity<List<DayDTO>> getAllDays(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Days");
        Page<DayDTO> page = dayService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/days");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /days/:id : get the "id" day.
     *
     * @param id the id of the dayDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dayDTO, or with status 404 (Not Found)
     */
    @GetMapping("/days/{id}")
    @Timed
    public ResponseEntity<DayDTO> getDay(@PathVariable Long id) {
        log.debug("REST request to get Day : {}", id);
        DayDTO dayDTO = dayService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dayDTO));
    }

    /**
     * DELETE  /days/:id : delete the "id" day.
     *
     * @param id the id of the dayDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/days/{id}")
    @Timed
    public ResponseEntity<Void> deleteDay(@PathVariable Long id) {
        log.debug("REST request to delete Day : {}", id);
        dayService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}

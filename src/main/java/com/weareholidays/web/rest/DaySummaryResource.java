package com.weareholidays.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.weareholidays.service.DaySummaryService;
import com.weareholidays.web.rest.util.HeaderUtil;
import com.weareholidays.web.rest.util.PaginationUtil;
import com.weareholidays.service.dto.DaySummaryDTO;
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
 * REST controller for managing DaySummary.
 */
@RestController
@RequestMapping("/api")
public class DaySummaryResource {

    private final Logger log = LoggerFactory.getLogger(DaySummaryResource.class);

    private static final String ENTITY_NAME = "daySummary";
        
    private final DaySummaryService daySummaryService;

    public DaySummaryResource(DaySummaryService daySummaryService) {
        this.daySummaryService = daySummaryService;
    }

    /**
     * POST  /day-summaries : Create a new daySummary.
     *
     * @param daySummaryDTO the daySummaryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new daySummaryDTO, or with status 400 (Bad Request) if the daySummary has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/day-summaries")
    @Timed
    public ResponseEntity<DaySummaryDTO> createDaySummary(@RequestBody DaySummaryDTO daySummaryDTO) throws URISyntaxException {
        log.debug("REST request to save DaySummary : {}", daySummaryDTO);
        if (daySummaryDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new daySummary cannot already have an ID")).body(null);
        }
        DaySummaryDTO result = daySummaryService.save(daySummaryDTO);
        return ResponseEntity.created(new URI("/api/day-summaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /day-summaries : Updates an existing daySummary.
     *
     * @param daySummaryDTO the daySummaryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated daySummaryDTO,
     * or with status 400 (Bad Request) if the daySummaryDTO is not valid,
     * or with status 500 (Internal Server Error) if the daySummaryDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/day-summaries")
    @Timed
    public ResponseEntity<DaySummaryDTO> updateDaySummary(@RequestBody DaySummaryDTO daySummaryDTO) throws URISyntaxException {
        log.debug("REST request to update DaySummary : {}", daySummaryDTO);
        if (daySummaryDTO.getId() == null) {
            return createDaySummary(daySummaryDTO);
        }
        DaySummaryDTO result = daySummaryService.save(daySummaryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, daySummaryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /day-summaries : get all the daySummaries.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of daySummaries in body
     */
    @GetMapping("/day-summaries")
    @Timed
    public ResponseEntity<List<DaySummaryDTO>> getAllDaySummaries(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of DaySummaries");
        Page<DaySummaryDTO> page = daySummaryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/day-summaries");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /day-summaries/:id : get the "id" daySummary.
     *
     * @param id the id of the daySummaryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the daySummaryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/day-summaries/{id}")
    @Timed
    public ResponseEntity<DaySummaryDTO> getDaySummary(@PathVariable Long id) {
        log.debug("REST request to get DaySummary : {}", id);
        DaySummaryDTO daySummaryDTO = daySummaryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(daySummaryDTO));
    }

    /**
     * DELETE  /day-summaries/:id : delete the "id" daySummary.
     *
     * @param id the id of the daySummaryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/day-summaries/{id}")
    @Timed
    public ResponseEntity<Void> deleteDaySummary(@PathVariable Long id) {
        log.debug("REST request to delete DaySummary : {}", id);
        daySummaryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}

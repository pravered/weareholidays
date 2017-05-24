package com.weareholidays.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.weareholidays.service.CheckInService;
import com.weareholidays.web.rest.util.HeaderUtil;
import com.weareholidays.web.rest.util.PaginationUtil;
import com.weareholidays.service.dto.CheckInDTO;
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
 * REST controller for managing CheckIn.
 */
@RestController
@RequestMapping("/api")
public class CheckInResource {

    private final Logger log = LoggerFactory.getLogger(CheckInResource.class);

    private static final String ENTITY_NAME = "checkIn";
        
    private final CheckInService checkInService;

    public CheckInResource(CheckInService checkInService) {
        this.checkInService = checkInService;
    }

    /**
     * POST  /check-ins : Create a new checkIn.
     *
     * @param checkInDTO the checkInDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new checkInDTO, or with status 400 (Bad Request) if the checkIn has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/check-ins")
    @Timed
    public ResponseEntity<CheckInDTO> createCheckIn(@RequestBody CheckInDTO checkInDTO) throws URISyntaxException {
        log.debug("REST request to save CheckIn : {}", checkInDTO);
        if (checkInDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new checkIn cannot already have an ID")).body(null);
        }
        CheckInDTO result = checkInService.save(checkInDTO);
        return ResponseEntity.created(new URI("/api/check-ins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /check-ins : Updates an existing checkIn.
     *
     * @param checkInDTO the checkInDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated checkInDTO,
     * or with status 400 (Bad Request) if the checkInDTO is not valid,
     * or with status 500 (Internal Server Error) if the checkInDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/check-ins")
    @Timed
    public ResponseEntity<CheckInDTO> updateCheckIn(@RequestBody CheckInDTO checkInDTO) throws URISyntaxException {
        log.debug("REST request to update CheckIn : {}", checkInDTO);
        if (checkInDTO.getId() == null) {
            return createCheckIn(checkInDTO);
        }
        CheckInDTO result = checkInService.save(checkInDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, checkInDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /check-ins : get all the checkIns.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of checkIns in body
     */
    @GetMapping("/check-ins")
    @Timed
    public ResponseEntity<List<CheckInDTO>> getAllCheckIns(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of CheckIns");
        Page<CheckInDTO> page = checkInService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/check-ins");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /check-ins/:id : get the "id" checkIn.
     *
     * @param id the id of the checkInDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the checkInDTO, or with status 404 (Not Found)
     */
    @GetMapping("/check-ins/{id}")
    @Timed
    public ResponseEntity<CheckInDTO> getCheckIn(@PathVariable Long id) {
        log.debug("REST request to get CheckIn : {}", id);
        CheckInDTO checkInDTO = checkInService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(checkInDTO));
    }

    /**
     * DELETE  /check-ins/:id : delete the "id" checkIn.
     *
     * @param id the id of the checkInDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/check-ins/{id}")
    @Timed
    public ResponseEntity<Void> deleteCheckIn(@PathVariable Long id) {
        log.debug("REST request to delete CheckIn : {}", id);
        checkInService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}

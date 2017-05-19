package com.weareholidays.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.weareholidays.service.CouponService;
import com.weareholidays.web.rest.util.HeaderUtil;
import com.weareholidays.web.rest.util.PaginationUtil;
import com.weareholidays.service.dto.CouponDTO;
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
 * REST controller for managing Coupon.
 */
@RestController
@RequestMapping("/api")
public class CouponResource {

    private final Logger log = LoggerFactory.getLogger(CouponResource.class);

    private static final String ENTITY_NAME = "coupon";
        
    private final CouponService couponService;

    public CouponResource(CouponService couponService) {
        this.couponService = couponService;
    }

    /**
     * POST  /coupons : Create a new coupon.
     *
     * @param couponDTO the couponDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new couponDTO, or with status 400 (Bad Request) if the coupon has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/coupons")
    @Timed
    public ResponseEntity<CouponDTO> createCoupon(@RequestBody CouponDTO couponDTO) throws URISyntaxException {
        log.debug("REST request to save Coupon : {}", couponDTO);
        if (couponDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new coupon cannot already have an ID")).body(null);
        }
        CouponDTO result = couponService.save(couponDTO);
        return ResponseEntity.created(new URI("/api/coupons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /coupons : Updates an existing coupon.
     *
     * @param couponDTO the couponDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated couponDTO,
     * or with status 400 (Bad Request) if the couponDTO is not valid,
     * or with status 500 (Internal Server Error) if the couponDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/coupons")
    @Timed
    public ResponseEntity<CouponDTO> updateCoupon(@RequestBody CouponDTO couponDTO) throws URISyntaxException {
        log.debug("REST request to update Coupon : {}", couponDTO);
        if (couponDTO.getId() == null) {
            return createCoupon(couponDTO);
        }
        CouponDTO result = couponService.save(couponDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, couponDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /coupons : get all the coupons.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of coupons in body
     */
    @GetMapping("/coupons")
    @Timed
    public ResponseEntity<List<CouponDTO>> getAllCoupons(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Coupons");
        Page<CouponDTO> page = couponService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/coupons");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /coupons/:id : get the "id" coupon.
     *
     * @param id the id of the couponDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the couponDTO, or with status 404 (Not Found)
     */
    @GetMapping("/coupons/{id}")
    @Timed
    public ResponseEntity<CouponDTO> getCoupon(@PathVariable Long id) {
        log.debug("REST request to get Coupon : {}", id);
        CouponDTO couponDTO = couponService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(couponDTO));
    }

    /**
     * DELETE  /coupons/:id : delete the "id" coupon.
     *
     * @param id the id of the couponDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/coupons/{id}")
    @Timed
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long id) {
        log.debug("REST request to delete Coupon : {}", id);
        couponService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}

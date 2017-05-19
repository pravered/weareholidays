package com.weareholidays.service.impl;

import com.weareholidays.service.CouponService;
import com.weareholidays.domain.Coupon;
import com.weareholidays.repository.CouponRepository;
import com.weareholidays.service.dto.CouponDTO;
import com.weareholidays.service.mapper.CouponMapper;
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
 * Service Implementation for managing Coupon.
 */
@Service
@Transactional
public class CouponServiceImpl implements CouponService{

    private final Logger log = LoggerFactory.getLogger(CouponServiceImpl.class);
    
    private final CouponRepository couponRepository;

    private final CouponMapper couponMapper;

    public CouponServiceImpl(CouponRepository couponRepository, CouponMapper couponMapper) {
        this.couponRepository = couponRepository;
        this.couponMapper = couponMapper;
    }

    /**
     * Save a coupon.
     *
     * @param couponDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CouponDTO save(CouponDTO couponDTO) {
        log.debug("Request to save Coupon : {}", couponDTO);
        Coupon coupon = couponMapper.couponDTOToCoupon(couponDTO);
        coupon = couponRepository.save(coupon);
        CouponDTO result = couponMapper.couponToCouponDTO(coupon);
        return result;
    }

    /**
     *  Get all the coupons.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CouponDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Coupons");
        Page<Coupon> result = couponRepository.findAll(pageable);
        return result.map(coupon -> couponMapper.couponToCouponDTO(coupon));
    }

    /**
     *  Get one coupon by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CouponDTO findOne(Long id) {
        log.debug("Request to get Coupon : {}", id);
        Coupon coupon = couponRepository.findOne(id);
        CouponDTO couponDTO = couponMapper.couponToCouponDTO(coupon);
        return couponDTO;
    }

    /**
     *  Delete the  coupon by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Coupon : {}", id);
        couponRepository.delete(id);
    }
}

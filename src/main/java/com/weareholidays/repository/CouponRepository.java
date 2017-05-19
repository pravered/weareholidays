package com.weareholidays.repository;

import com.weareholidays.domain.Coupon;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Coupon entity.
 */
@SuppressWarnings("unused")
public interface CouponRepository extends JpaRepository<Coupon,Long> {

}

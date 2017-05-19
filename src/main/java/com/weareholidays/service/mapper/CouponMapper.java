package com.weareholidays.service.mapper;

import com.weareholidays.domain.*;
import com.weareholidays.service.dto.CouponDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Coupon and its DTO CouponDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CouponMapper {

    CouponDTO couponToCouponDTO(Coupon coupon);

    List<CouponDTO> couponsToCouponDTOs(List<Coupon> coupons);

    Coupon couponDTOToCoupon(CouponDTO couponDTO);

    List<Coupon> couponDTOsToCoupons(List<CouponDTO> couponDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Coupon couponFromId(Long id) {
        if (id == null) {
            return null;
        }
        Coupon coupon = new Coupon();
        coupon.setId(id);
        return coupon;
    }
    

}

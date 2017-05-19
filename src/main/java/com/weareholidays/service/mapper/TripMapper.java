package com.weareholidays.service.mapper;

import com.weareholidays.domain.*;
import com.weareholidays.service.dto.TripDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Trip and its DTO TripDTO.
 */
@Mapper(componentModel = "spring", uses = {TripSummaryMapper.class, TripSettingsMapper.class, UserMapper.class, CouponMapper.class, })
public interface TripMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "coupon.id", target = "couponId")
    TripDTO tripToTripDTO(Trip trip);

    List<TripDTO> tripsToTripDTOs(List<Trip> trips);

    @Mapping(target = "days", ignore = true)
    @Mapping(target = "tripPeople", ignore = true)
    @Mapping(source = "userId", target = "user")
    @Mapping(source = "couponId", target = "coupon")
    Trip tripDTOToTrip(TripDTO tripDTO);

    List<Trip> tripDTOsToTrips(List<TripDTO> tripDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */

    default Trip tripFromId(Long id) {
        if (id == null) {
            return null;
        }
        Trip trip = new Trip();
        trip.setId(id);
        return trip;
    }


}

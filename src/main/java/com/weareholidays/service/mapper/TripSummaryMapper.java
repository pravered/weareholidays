package com.weareholidays.service.mapper;

import com.weareholidays.domain.*;
import com.weareholidays.service.dto.TripSummaryDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity TripSummary and its DTO TripSummaryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TripSummaryMapper {

    TripSummaryDTO tripSummaryToTripSummaryDTO(TripSummary tripSummary);

    List<TripSummaryDTO> tripSummariesToTripSummaryDTOs(List<TripSummary> tripSummaries);

    TripSummary tripSummaryDTOToTripSummary(TripSummaryDTO tripSummaryDTO);

    List<TripSummary> tripSummaryDTOsToTripSummaries(List<TripSummaryDTO> tripSummaryDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default TripSummary tripSummaryFromId(Long id) {
        if (id == null) {
            return null;
        }
        TripSummary tripSummary = new TripSummary();
        tripSummary.setId(id);
        return tripSummary;
    }
    

}

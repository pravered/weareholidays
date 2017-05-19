package com.weareholidays.service.mapper;

import com.weareholidays.domain.*;
import com.weareholidays.service.dto.TripPeopleDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity TripPeople and its DTO TripPeopleDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TripPeopleMapper {

    TripPeopleDTO tripPeopleToTripPeopleDTO(TripPeople tripPeople);

    List<TripPeopleDTO> tripPeopleToTripPeopleDTOs(List<TripPeople> tripPeople);

    TripPeople tripPeopleDTOToTripPeople(TripPeopleDTO tripPeopleDTO);

    List<TripPeople> tripPeopleDTOsToTripPeople(List<TripPeopleDTO> tripPeopleDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default TripPeople tripPeopleFromId(Long id) {
        if (id == null) {
            return null;
        }
        TripPeople tripPeople = new TripPeople();
        tripPeople.setId(id);
        return tripPeople;
    }
    

}

package com.weareholidays.service.mapper;

import com.weareholidays.domain.*;
import com.weareholidays.service.dto.CheckInDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CheckIn and its DTO CheckInDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CheckInMapper {

    CheckInDTO checkInToCheckInDTO(CheckIn checkIn);

    List<CheckInDTO> checkInsToCheckInDTOs(List<CheckIn> checkIns);

    CheckIn checkInDTOToCheckIn(CheckInDTO checkInDTO);

    List<CheckIn> checkInDTOsToCheckIns(List<CheckInDTO> checkInDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default CheckIn checkInFromId(Long id) {
        if (id == null) {
            return null;
        }
        CheckIn checkIn = new CheckIn();
        checkIn.setId(id);
        return checkIn;
    }
    

}

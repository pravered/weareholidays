package com.weareholidays.service.mapper;

import com.weareholidays.domain.*;
import com.weareholidays.service.dto.TripSettingsDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity TripSettings and its DTO TripSettingsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TripSettingsMapper {

    TripSettingsDTO tripSettingsToTripSettingsDTO(TripSettings tripSettings);

    List<TripSettingsDTO> tripSettingsToTripSettingsDTOs(List<TripSettings> tripSettings);

    TripSettings tripSettingsDTOToTripSettings(TripSettingsDTO tripSettingsDTO);

    List<TripSettings> tripSettingsDTOsToTripSettings(List<TripSettingsDTO> tripSettingsDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default TripSettings tripSettingsFromId(Long id) {
        if (id == null) {
            return null;
        }
        TripSettings tripSettings = new TripSettings();
        tripSettings.setId(id);
        return tripSettings;
    }
    

}

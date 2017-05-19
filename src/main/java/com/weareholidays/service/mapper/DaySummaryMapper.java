package com.weareholidays.service.mapper;

import com.weareholidays.domain.*;
import com.weareholidays.service.dto.DaySummaryDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity DaySummary and its DTO DaySummaryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DaySummaryMapper {

    DaySummaryDTO daySummaryToDaySummaryDTO(DaySummary daySummary);

    List<DaySummaryDTO> daySummariesToDaySummaryDTOs(List<DaySummary> daySummaries);

    DaySummary daySummaryDTOToDaySummary(DaySummaryDTO daySummaryDTO);

    List<DaySummary> daySummaryDTOsToDaySummaries(List<DaySummaryDTO> daySummaryDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default DaySummary daySummaryFromId(Long id) {
        if (id == null) {
            return null;
        }
        DaySummary daySummary = new DaySummary();
        daySummary.setId(id);
        return daySummary;
    }
    

}

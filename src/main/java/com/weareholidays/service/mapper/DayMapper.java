package com.weareholidays.service.mapper;

import com.weareholidays.domain.*;
import com.weareholidays.service.dto.DayDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Day and its DTO DayDTO.
 */
@Mapper(componentModel = "spring", uses = {DaySummaryMapper.class, })
public interface DayMapper {

    DayDTO dayToDayDTO(Day day);

    List<DayDTO> daysToDayDTOs(List<Day> days);

    Day dayDTOToDay(DayDTO dayDTO);

    List<Day> dayDTOsToDays(List<DayDTO> dayDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */

    default Day dayFromId(Long id) {
        if (id == null) {
            return null;
        }
        Day day = new Day();
        day.setId(id);
        return day;
    }


}

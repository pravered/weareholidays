package com.weareholidays.service.mapper;

import com.weareholidays.domain.*;
import com.weareholidays.service.dto.TimelineDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Timeline and its DTO TimelineDTO.
 */
@Mapper(componentModel = "spring", uses = {ContentMapper.class, DayMapper.class, })
public interface TimelineMapper {

    @Mapping(source = "day.id", target = "dayId")
    TimelineDTO timelineToTimelineDTO(Timeline timeline);

    List<TimelineDTO> timelinesToTimelineDTOs(List<Timeline> timelines);

    @Mapping(source = "dayId", target = "day")
    Timeline timelineDTOToTimeline(TimelineDTO timelineDTO);

    List<Timeline> timelineDTOsToTimelines(List<TimelineDTO> timelineDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */

    default Timeline timelineFromId(Long id) {
        if (id == null) {
            return null;
        }
        Timeline timeline = new Timeline();
        timeline.setId(id);
        return timeline;
    }


}

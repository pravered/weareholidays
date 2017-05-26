package com.weareholidays.service.mapper;

import com.weareholidays.domain.*;
import com.weareholidays.service.dto.ContentDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Content and its DTO ContentDTO.
 */
@Mapper(componentModel = "spring", uses = {MediaMapper.class, AlbumMapper.class, CheckInMapper.class, })
public interface ContentMapper {

    ContentDTO contentToContentDTO(Content content);

    List<ContentDTO> contentsToContentDTOs(List<Content> contents);

    Content contentDTOToContent(ContentDTO contentDTO);

    List<Content> contentDTOsToContents(List<ContentDTO> contentDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */

    default Content contentFromId(Long id) {
        if (id == null) {
            return null;
        }
        Content content = new Content();
        content.setId(id);
        return content;
    }


}

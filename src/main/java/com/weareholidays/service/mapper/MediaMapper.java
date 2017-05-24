package com.weareholidays.service.mapper;

import com.weareholidays.domain.*;
import com.weareholidays.service.dto.MediaDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Media and its DTO MediaDTO.
 */
@Mapper(componentModel = "spring", uses = {AlbumMapper.class, })
public interface MediaMapper {

    @Mapping(source = "album.id", target = "albumId")
    MediaDTO mediaToMediaDTO(Media media);

    List<MediaDTO> mediaToMediaDTOs(List<Media> media);

    @Mapping(source = "albumId", target = "album")
    Media mediaDTOToMedia(MediaDTO mediaDTO);

    List<Media> mediaDTOsToMedia(List<MediaDTO> mediaDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Media mediaFromId(Long id) {
        if (id == null) {
            return null;
        }
        Media media = new Media();
        media.setId(id);
        return media;
    }
    

}

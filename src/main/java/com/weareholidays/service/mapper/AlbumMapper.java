package com.weareholidays.service.mapper;

import com.weareholidays.domain.*;
import com.weareholidays.service.dto.AlbumDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Album and its DTO AlbumDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AlbumMapper {

    AlbumDTO albumToAlbumDTO(Album album);

    List<AlbumDTO> albumsToAlbumDTOs(List<Album> albums);

    @Mapping(target = "media", ignore = true)
    Album albumDTOToAlbum(AlbumDTO albumDTO);

    List<Album> albumDTOsToAlbums(List<AlbumDTO> albumDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Album albumFromId(Long id) {
        if (id == null) {
            return null;
        }
        Album album = new Album();
        album.setId(id);
        return album;
    }
    

}

package com.weareholidays.service;

import com.weareholidays.service.dto.MediaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Media.
 */
public interface MediaService {

    /**
     * Save a media.
     *
     * @param mediaDTO the entity to save
     * @return the persisted entity
     */
    MediaDTO save(MediaDTO mediaDTO);

    /**
     *  Get all the media.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MediaDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" media.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MediaDTO findOne(Long id);

    /**
     *  Delete the "id" media.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

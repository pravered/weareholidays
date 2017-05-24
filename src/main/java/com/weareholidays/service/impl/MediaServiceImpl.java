package com.weareholidays.service.impl;

import com.weareholidays.service.MediaService;
import com.weareholidays.domain.Media;
import com.weareholidays.repository.MediaRepository;
import com.weareholidays.service.dto.MediaDTO;
import com.weareholidays.service.mapper.MediaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Media.
 */
@Service
@Transactional
public class MediaServiceImpl implements MediaService{

    private final Logger log = LoggerFactory.getLogger(MediaServiceImpl.class);
    
    private final MediaRepository mediaRepository;

    private final MediaMapper mediaMapper;

    public MediaServiceImpl(MediaRepository mediaRepository, MediaMapper mediaMapper) {
        this.mediaRepository = mediaRepository;
        this.mediaMapper = mediaMapper;
    }

    /**
     * Save a media.
     *
     * @param mediaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MediaDTO save(MediaDTO mediaDTO) {
        log.debug("Request to save Media : {}", mediaDTO);
        Media media = mediaMapper.mediaDTOToMedia(mediaDTO);
        media = mediaRepository.save(media);
        MediaDTO result = mediaMapper.mediaToMediaDTO(media);
        return result;
    }

    /**
     *  Get all the media.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MediaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Media");
        Page<Media> result = mediaRepository.findAll(pageable);
        return result.map(media -> mediaMapper.mediaToMediaDTO(media));
    }

    /**
     *  Get one media by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MediaDTO findOne(Long id) {
        log.debug("Request to get Media : {}", id);
        Media media = mediaRepository.findOne(id);
        MediaDTO mediaDTO = mediaMapper.mediaToMediaDTO(media);
        return mediaDTO;
    }

    /**
     *  Delete the  media by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Media : {}", id);
        mediaRepository.delete(id);
    }
}

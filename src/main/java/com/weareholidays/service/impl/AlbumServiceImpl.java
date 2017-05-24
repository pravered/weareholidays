package com.weareholidays.service.impl;

import com.weareholidays.service.AlbumService;
import com.weareholidays.domain.Album;
import com.weareholidays.repository.AlbumRepository;
import com.weareholidays.service.dto.AlbumDTO;
import com.weareholidays.service.mapper.AlbumMapper;
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
 * Service Implementation for managing Album.
 */
@Service
@Transactional
public class AlbumServiceImpl implements AlbumService{

    private final Logger log = LoggerFactory.getLogger(AlbumServiceImpl.class);
    
    private final AlbumRepository albumRepository;

    private final AlbumMapper albumMapper;

    public AlbumServiceImpl(AlbumRepository albumRepository, AlbumMapper albumMapper) {
        this.albumRepository = albumRepository;
        this.albumMapper = albumMapper;
    }

    /**
     * Save a album.
     *
     * @param albumDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AlbumDTO save(AlbumDTO albumDTO) {
        log.debug("Request to save Album : {}", albumDTO);
        Album album = albumMapper.albumDTOToAlbum(albumDTO);
        album = albumRepository.save(album);
        AlbumDTO result = albumMapper.albumToAlbumDTO(album);
        return result;
    }

    /**
     *  Get all the albums.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AlbumDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Albums");
        Page<Album> result = albumRepository.findAll(pageable);
        return result.map(album -> albumMapper.albumToAlbumDTO(album));
    }

    /**
     *  Get one album by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AlbumDTO findOne(Long id) {
        log.debug("Request to get Album : {}", id);
        Album album = albumRepository.findOne(id);
        AlbumDTO albumDTO = albumMapper.albumToAlbumDTO(album);
        return albumDTO;
    }

    /**
     *  Delete the  album by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Album : {}", id);
        albumRepository.delete(id);
    }
}

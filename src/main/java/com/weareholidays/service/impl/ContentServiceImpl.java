package com.weareholidays.service.impl;

import com.weareholidays.service.ContentService;
import com.weareholidays.domain.Content;
import com.weareholidays.repository.ContentRepository;
import com.weareholidays.service.dto.ContentDTO;
import com.weareholidays.service.mapper.ContentMapper;
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
 * Service Implementation for managing Content.
 */
@Service
@Transactional
public class ContentServiceImpl implements ContentService{

    private final Logger log = LoggerFactory.getLogger(ContentServiceImpl.class);
    
    private final ContentRepository contentRepository;

    private final ContentMapper contentMapper;

    public ContentServiceImpl(ContentRepository contentRepository, ContentMapper contentMapper) {
        this.contentRepository = contentRepository;
        this.contentMapper = contentMapper;
    }

    /**
     * Save a content.
     *
     * @param contentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ContentDTO save(ContentDTO contentDTO) {
        log.debug("Request to save Content : {}", contentDTO);
        Content content = contentMapper.contentDTOToContent(contentDTO);
        content = contentRepository.save(content);
        ContentDTO result = contentMapper.contentToContentDTO(content);
        return result;
    }

    /**
     *  Get all the contents.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ContentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Contents");
        Page<Content> result = contentRepository.findAll(pageable);
        return result.map(content -> contentMapper.contentToContentDTO(content));
    }

    /**
     *  Get one content by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ContentDTO findOne(Long id) {
        log.debug("Request to get Content : {}", id);
        Content content = contentRepository.findOne(id);
        ContentDTO contentDTO = contentMapper.contentToContentDTO(content);
        return contentDTO;
    }

    /**
     *  Delete the  content by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Content : {}", id);
        contentRepository.delete(id);
    }
}

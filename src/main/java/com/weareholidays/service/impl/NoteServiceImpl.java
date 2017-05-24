package com.weareholidays.service.impl;

import com.weareholidays.service.NoteService;
import com.weareholidays.domain.Note;
import com.weareholidays.repository.NoteRepository;
import com.weareholidays.service.dto.NoteDTO;
import com.weareholidays.service.mapper.NoteMapper;
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
 * Service Implementation for managing Note.
 */
@Service
@Transactional
public class NoteServiceImpl implements NoteService{

    private final Logger log = LoggerFactory.getLogger(NoteServiceImpl.class);
    
    private final NoteRepository noteRepository;

    private final NoteMapper noteMapper;

    public NoteServiceImpl(NoteRepository noteRepository, NoteMapper noteMapper) {
        this.noteRepository = noteRepository;
        this.noteMapper = noteMapper;
    }

    /**
     * Save a note.
     *
     * @param noteDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public NoteDTO save(NoteDTO noteDTO) {
        log.debug("Request to save Note : {}", noteDTO);
        Note note = noteMapper.noteDTOToNote(noteDTO);
        note = noteRepository.save(note);
        NoteDTO result = noteMapper.noteToNoteDTO(note);
        return result;
    }

    /**
     *  Get all the notes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<NoteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Notes");
        Page<Note> result = noteRepository.findAll(pageable);
        return result.map(note -> noteMapper.noteToNoteDTO(note));
    }

    /**
     *  Get one note by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public NoteDTO findOne(Long id) {
        log.debug("Request to get Note : {}", id);
        Note note = noteRepository.findOne(id);
        NoteDTO noteDTO = noteMapper.noteToNoteDTO(note);
        return noteDTO;
    }

    /**
     *  Delete the  note by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Note : {}", id);
        noteRepository.delete(id);
    }
}

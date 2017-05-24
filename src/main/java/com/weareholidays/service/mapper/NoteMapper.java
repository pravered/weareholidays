package com.weareholidays.service.mapper;

import com.weareholidays.domain.*;
import com.weareholidays.service.dto.NoteDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Note and its DTO NoteDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NoteMapper {

    NoteDTO noteToNoteDTO(Note note);

    List<NoteDTO> notesToNoteDTOs(List<Note> notes);

    Note noteDTOToNote(NoteDTO noteDTO);

    List<Note> noteDTOsToNotes(List<NoteDTO> noteDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Note noteFromId(Long id) {
        if (id == null) {
            return null;
        }
        Note note = new Note();
        note.setId(id);
        return note;
    }
    

}

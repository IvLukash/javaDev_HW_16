package goit.edu.service;

import goit.edu.model.entity.Note;
import goit.edu.model.dto.NoteDto;

import java.util.List;
import java.util.UUID;

public interface NoteService {

    NoteDto getById(Long id, UUID userId);

    List<NoteDto> listAllByUserId(UUID userId);

    Note add(NoteDto noteDto, UUID userId);

    void deleteById(Long id, UUID userId);

    void update(Long id, UUID userId, NoteDto noteDto);
}

package goit.edu.service;

import goit.edu.model.Note;
import goit.edu.model.NoteDto;
import goit.edu.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService{
    private final NoteRepository repository;

    @Override
    public NoteDto getById(Long id, UUID userId) {
        Note note = repository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        if (!note.getUserId().equals(userId)) {
            throw new SecurityException();
        }
        return convertToDto(note);
    }

    @Override
    public List<NoteDto> listAllByUserId(UUID userId) {
        return repository.findAllByUserId(userId).stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public Note add(NoteDto noteDto, UUID userId) {
        Note note = convertToModel(noteDto);
        note.setUserId(userId);
        return repository.save(note);
    }

    @Override
    public void deleteById(Long id, UUID userId) {
        Note note = repository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        if (!note.getUserId().equals(userId)) {
            throw new SecurityException();
        }
        repository.deleteById(id);
    }

    @Override
    public void update(Long id, UUID userId, NoteDto noteDto) {
        Note updatedNote = repository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        if (!updatedNote.getUserId().equals(userId)) {
            throw new SecurityException();
        }
        updatedNote.setTitle(noteDto.getTitle());
        updatedNote.setContent(noteDto.getContent());
        repository.save(updatedNote);
    }

    private NoteDto convertToDto(Note note) {
        return new NoteDto(
                note.getId(),
                note.getTitle(),
                note.getContent()
        );
    }

    private Note convertToModel(NoteDto noteDto) {
        return new Note(
                noteDto.getTitle(),
                noteDto.getContent()
        );
    }
}

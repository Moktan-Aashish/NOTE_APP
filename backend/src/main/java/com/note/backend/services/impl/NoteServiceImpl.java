package com.note.backend.services.impl;

import com.note.backend.dtos.note.NoteRequestDto;
import com.note.backend.dtos.note.NoteResponseDto;
import com.note.backend.exception.customs.FolderNotFoundException;
import com.note.backend.exception.customs.NoteNotFoundException;
import com.note.backend.mappers.note.NoteDtoMapper;
import com.note.backend.models.Note;
import com.note.backend.repositories.FolderRepository;
import com.note.backend.repositories.NoteRepository;
import com.note.backend.services.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteDtoMapper noteDtoMapper;
    private final NoteRepository noteRepository;
    private final FolderRepository folderRepository;

    @Override
    public List<NoteResponseDto> getAllNotes() {
        log.info("Fetching all the notes");
        List<Note> notes = noteRepository.findAll();

        if (notes.isEmpty()) {
            log.info("No notes found");
            return Collections.emptyList();
        }

        log.info("Found {} notes", notes.size());
        return noteDtoMapper.toDto(notes);
    }

    @Override
    public NoteResponseDto createNewNote(NoteRequestDto dto) {
        log.info("Creating new note");
        Note note = noteDtoMapper.toEntity(dto);

        Note savedNote = noteRepository.save(note);

        log.info("Note created with id: {}", savedNote.getId());
        return noteDtoMapper.toDto(savedNote);
    }

    @Override
    public NoteResponseDto moveNoteToFolder(String noteId, String folderId) {
        log.info("Moving note to folder with id: {}", folderId);
        Note existing = noteRepository.findById(noteId).orElseThrow(() -> new NoteNotFoundException("Note not found"));

        if (folderId != null) {
            folderRepository.findById(folderId).orElseThrow(() -> new FolderNotFoundException("Folder not found"));
        }

        existing.setFolderId(folderId);
        Note updatedNote = noteRepository.save(existing);

        log.info("Note moved to folder with id: {}", updatedNote.getFolderId());
        return noteDtoMapper.toDto(updatedNote);
    }

    @Override
    public NoteResponseDto updateNote(String noteId, NoteRequestDto dto) {
        log.info("Updating note with id: {}", noteId);
        Note existing = noteRepository.findById(noteId).orElseThrow(() -> new NoteNotFoundException("Note not found"));
        existing.setTitle(dto.getTitle());
        existing.setContent(dto.getContent());

        Note updatedNote = noteRepository.save(existing);

        log.info("Updated note with id: {}", updatedNote.getId());
        return noteDtoMapper.toDto(updatedNote);
    }

    @Override
    public void deleteNote(String noteId) {
        log.info("Deleting note with id: {}", noteId);

        noteRepository.findById(noteId).orElseThrow(() -> new NoteNotFoundException("Note not found"));
        noteRepository.deleteById(noteId);
        log.info("Note deleted with id: {}", noteId);
    }
}

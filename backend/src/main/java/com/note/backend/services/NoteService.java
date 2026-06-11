package com.note.backend.services;

import com.note.backend.dtos.note.NoteRequestDto;
import com.note.backend.dtos.note.NoteResponseDto;

import java.util.List;

public interface NoteService {
    List<NoteResponseDto> getAllNotes();

    NoteResponseDto createNewNote(NoteRequestDto dto);

    NoteResponseDto moveNoteToFolder(String noteId, String folderId);

    NoteResponseDto updateNote(String noteId, NoteRequestDto dto);

    void deleteNote(String noteId);
}

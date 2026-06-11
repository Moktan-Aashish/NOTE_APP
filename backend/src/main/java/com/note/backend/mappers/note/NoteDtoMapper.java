package com.note.backend.mappers.note;

import com.note.backend.dtos.note.NoteRequestDto;
import com.note.backend.dtos.note.NoteResponseDto;
import com.note.backend.models.Note;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NoteDtoMapper {

    public NoteResponseDto toDto(Note note) {
        return NoteResponseDto.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .folderId(note.getFolderId())
                .createdAt(note.getCreatedAt())
                .updatedAt(note.getUpdatedAt())
                .build();
    }

    public List<NoteResponseDto> toDto(List<Note> notes) {
        return notes.stream()
                .map(this::toDto)
                .toList();
    }

    public Note toEntity(NoteRequestDto dto) {
        return Note.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();
    }
}

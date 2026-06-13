package com.note.backend.controllers;

import com.note.backend.dtos.note.NoteRequestDto;
import com.note.backend.dtos.note.NoteResponseDto;
import com.note.backend.dtos.response.ApiResponse;
import com.note.backend.services.NoteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<NoteResponseDto>>> getAllNotes(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(
                        "Notes fetched successfully",
                        noteService.getAllNotes(),
                        request.getRequestURI()
                )
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<NoteResponseDto>> addNewNote(
            @Valid @RequestBody NoteRequestDto dto,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success(
                        "Note added successfully",
                        noteService.createNewNote(dto),
                        request.getRequestURI()
                )
        );
    }

    @PatchMapping("/{noteId}/move")
    public ResponseEntity<ApiResponse<NoteResponseDto>> moveNoteToFolder(
            @PathVariable @NotBlank(message = "Note id cannot be null or empty") String noteId,
            @RequestParam(required = false) String folderId,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(
                        "Note moved to folder successfully",
                        noteService.moveNoteToFolder(noteId, folderId),
                        request.getRequestURI()
                )
        );
    }

    @PutMapping("/{noteId}")
    public ResponseEntity<ApiResponse<NoteResponseDto>> updateNote(
            @PathVariable @NotBlank(message = "Note id cannot be null or empty") String noteId,
            @Valid @RequestBody NoteRequestDto dto,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(
                        "Note updated successfully",
                        noteService.updateNote(noteId, dto),
                        request.getRequestURI()
                )
        );
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<ApiResponse<Object>> deleteNote(
            @PathVariable @NotBlank(message = "Note id cannot be null or empty") String noteId,
            HttpServletRequest request
    ) {
        noteService.deleteNote(noteId);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(
                        "Note deleted successfully",
                        request.getRequestURI()
                )
        );
    }
}

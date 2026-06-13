package com.note.backend.controllers;

import com.note.backend.dtos.note.NoteRequestDto;
import com.note.backend.dtos.note.NoteResponseDto;
import com.note.backend.dtos.response.ApiResponse;
import com.note.backend.services.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Notes", description = "Endpoints for managing the notes")
public class NoteController {

    private final NoteService noteService;

    @Operation(summary = "Fetch all notes")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "List of the notes returned"
    )
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

    @Operation(
            summary = "Create a new note",
            description = "Creates a new note and returns the saved note"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "New note created successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Invalid request body provided"
            )
    })
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

    @Operation(
            summary = "Move to or out of folder",
            description = "Moves the current note to or out of the folder"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Moved the note to the folder successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Could not find the folder or the note"
            )
    })
    @PatchMapping("/{noteId}/move")
    public ResponseEntity<ApiResponse<NoteResponseDto>> moveNoteToFolder(
            @Parameter(description = "Unique Id of the note")
            @NotBlank(message = "Note id cannot be null or empty")
            @PathVariable
            String noteId,
            @Parameter(description = "Unique Id of the folder")
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

    @Operation(
            summary = "Update the note",
            description = "Updates the notes title or content and returns the updated note"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Note updated successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Invalid request body provided"
            )
    })
    @PutMapping("/{noteId}")
    public ResponseEntity<ApiResponse<NoteResponseDto>> updateNote(
            @Parameter(description = "Unique Id of the note")
            @NotBlank(message = "Note id cannot be null or empty")
            @PathVariable String noteId,
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

    @Operation(summary = "Delete a note")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Note deleted successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Could not find the note for deleting"
            )
    })
    @DeleteMapping("/{noteId}")
    public ResponseEntity<ApiResponse<Object>> deleteNote(
            @Parameter(description = "Unique Id of the note")
            @NotBlank(message = "Note id cannot be null or empty")
            @PathVariable String noteId,
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

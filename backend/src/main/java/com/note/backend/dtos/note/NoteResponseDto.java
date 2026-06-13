package com.note.backend.dtos.note;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response data for notes")
public class NoteResponseDto {

    @Schema(description = "Id of the note")
    private String id;

    @Schema(
            description = "Title of the note",
            example = "New note"
    )
    private String title;

    @Schema(
            description = "Content of the note",
            example = "This is my note"
    )
    private String  content;

    @Schema(description = "Id of the Folder the note belongs to")
    private String folderId;

    @Schema(
            description = "Creation timestamp of the note",
            example = "2026-06-13T17:30:00"
    )
    private LocalDateTime createdAt;

    @Schema(
            description = "Updated time of the note",
            example = "2026-06-13T17:30:00"
    )
    private LocalDateTime updatedAt;
}

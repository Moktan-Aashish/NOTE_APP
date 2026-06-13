package com.note.backend.dtos.note;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request body for creating and updating notes")
public class NoteRequestDto {

    @Schema(
            description = "Title of the note",
            example = "New note",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Note title cannot be blank")
    @Size(min = 1, max = 100, message = "Note title must be between 1 and 100 characters")
    private String title;

    @Schema(
            description = "Content of the note",
            example = "This is my new note.",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Note content cannot be blank")
    private String content;
}

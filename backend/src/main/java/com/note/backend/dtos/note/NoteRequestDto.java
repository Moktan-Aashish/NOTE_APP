package com.note.backend.dtos.note;

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
public class NoteRequestDto {

    @NotBlank(message = "Note title cannot be blank")
    @Size(min = 1, max = 100, message = "Note title must be between 1 and 100 characters")
    private String title;

    @NotBlank(message = "Note content cannot be blank")
    private String content;
}

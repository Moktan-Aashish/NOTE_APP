package com.note.backend.dtos.folder;

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
public class FolderRequestDto {

    @NotBlank(message = "Folder name cannot be blank")
    @Size(min = 1, max = 50, message = "Folder name must be between 1 and 50")
    private String name;
}

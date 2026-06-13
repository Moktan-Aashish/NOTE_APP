package com.note.backend.dtos.folder;

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
@Schema(description = "Request body for creating updating the folder")
public class FolderRequestDto {

    @Schema(
            description = "Name of the folder",
            example = "New folder",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Folder name cannot be blank")
    @Size(min = 1, max = 50, message = "Folder name must be between 1 and 50")
    private String name;
}

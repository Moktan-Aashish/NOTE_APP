package com.note.backend.dtos.folder;

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
@Schema(description = "Response data for folder")
public class FolderResponseDto {

    @Schema(description = "Id of the folder")
    private String id;

    @Schema(
            description = "Name of the folder",
            example = "New folder"
    )
    private String name;

    @Schema(
            description = "Creation time of the folder",
            example = "2026-06-13T17:30:00"
    )
    private LocalDateTime createdAt;
}

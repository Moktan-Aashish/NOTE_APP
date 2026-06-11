package com.note.backend.dtos.note;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteResponseDto {

    private String id;
    private String title;
    private String  content;
    private String folderId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

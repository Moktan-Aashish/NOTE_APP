package com.note.backend.mappers.folder;

import com.note.backend.dtos.folder.FolderRequestDto;
import com.note.backend.dtos.folder.FolderResponseDto;
import com.note.backend.models.Folder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FolderDtoMapper {

    public FolderResponseDto toDto(Folder folder) {
        return FolderResponseDto.builder()
                .id(folder.getId())
                .name(folder.getName())
                .createdAt(folder.getCreatedAt())
                .build();
    }

    public List<FolderResponseDto> toDto(List<Folder> folders) {
        return folders.stream()
                .map(this::toDto)
                .toList();
    }

    public Folder toEntity(FolderRequestDto dto) {
        return Folder.builder()
                .name(dto.getName())
                .build();
    }
}

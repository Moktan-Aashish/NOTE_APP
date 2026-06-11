package com.note.backend.services;

import com.note.backend.dtos.folder.FolderRequestDto;
import com.note.backend.dtos.folder.FolderResponseDto;
import com.note.backend.dtos.note.NoteResponseDto;

import java.util.List;

public interface FolderService {
    List<FolderResponseDto> getAllFolders();

    List<NoteResponseDto> getAllNoteOfFolder(String folderId);

    FolderResponseDto createNewFolder(FolderRequestDto dto);

    FolderResponseDto updateFolder(String folderId, FolderRequestDto dto);

    void deleteFolder(String folderId);
}

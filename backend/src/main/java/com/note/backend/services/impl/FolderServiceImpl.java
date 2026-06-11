package com.note.backend.services.impl;

import com.note.backend.dtos.folder.FolderRequestDto;
import com.note.backend.dtos.folder.FolderResponseDto;
import com.note.backend.dtos.note.NoteResponseDto;
import com.note.backend.exception.customs.FolderNotFoundException;
import com.note.backend.mappers.folder.FolderDtoMapper;
import com.note.backend.mappers.note.NoteDtoMapper;
import com.note.backend.models.Folder;
import com.note.backend.models.Note;
import com.note.backend.repositories.FolderRepository;
import com.note.backend.repositories.NoteRepository;
import com.note.backend.services.FolderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FolderServiceImpl implements FolderService {

    private final NoteDtoMapper noteDtoMapper;
    private final NoteRepository noteRepository;
    private final FolderDtoMapper folderDtoMapper;
    private final FolderRepository folderRepository;

    @Override
    public List<FolderResponseDto> getAllFolders() {
        log.info("Fetching all folders");
        List<Folder> folders = folderRepository.findAll();

        if (folders.isEmpty()) {
            log.warn("No folders found");
            return Collections.emptyList();
        }

        log.info("Found {} folders", folders.size());
        return folderDtoMapper.toDto(folders);
    }

    @Override
    public List<NoteResponseDto> getAllNoteOfFolder(String folderId) {
        log.info("Fetching all notes of folder with id: {}", folderId);
        List<Note> notes = noteRepository.findByFolderId(folderId);

        if (notes.isEmpty()) {
            log.info("No notes found for this folder");
            return Collections.emptyList();
        }

        log.info("Found {} notes", notes.size());
        return noteDtoMapper.toDto(notes);
    }

    @Override
    public FolderResponseDto createNewFolder(FolderRequestDto dto) {
        log.info("Creating folder with name {}", dto.getName());
        Folder newFolder = folderDtoMapper.toEntity(dto);

        Folder savedFolder = folderRepository.save(newFolder);

        log.info("Folder created with id: {}", savedFolder.getId());
        return folderDtoMapper.toDto(savedFolder);
    }

    @Override
    public FolderResponseDto updateFolder(String folderId, FolderRequestDto dto) {
        log.info("Updating folder with id: {}", folderId);
        Folder existing = folderRepository.findById(folderId).orElseThrow(() -> new FolderNotFoundException("Folder does not exist"));
        existing.setName(dto.getName());

        Folder updatedFolder = folderRepository.save(existing);

        log.info("Folder updated with id: {}", updatedFolder.getId());
        return folderDtoMapper.toDto(updatedFolder);
    }

    @Override
    public void deleteFolder(String folderId) {
        log.info("Deleting folder with id: {}", folderId);

        folderRepository.findById(folderId).orElseThrow(() -> new FolderNotFoundException("Folder does not exist"));
        folderRepository.deleteById(folderId);
        log.info("Folder deleted with id: {}", folderId);
    }
}

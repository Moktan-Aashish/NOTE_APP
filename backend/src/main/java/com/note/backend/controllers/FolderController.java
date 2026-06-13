package com.note.backend.controllers;

import com.note.backend.dtos.folder.FolderRequestDto;
import com.note.backend.dtos.folder.FolderResponseDto;
import com.note.backend.dtos.note.NoteResponseDto;
import com.note.backend.dtos.response.ApiResponse;
import com.note.backend.services.FolderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/folders")
public class FolderController {

    private final FolderService folderService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<FolderResponseDto>>> getAllFolders(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(
                        "Folders fetched successfully",
                        folderService.getAllFolders(),
                        request.getRequestURI()
                )
        );
    }

    @GetMapping("/{folderId}")
    public ResponseEntity<ApiResponse<List<NoteResponseDto>>> getFolderNotes(
            @PathVariable @NotBlank(message = "Folder id cannot be empty or null") String folderId,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(
                        "Notes of this folder fetched successfully",
                        folderService.getAllNoteOfFolder(folderId),
                        request.getRequestURI()
                )
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<FolderResponseDto>> createNewFolder(
            @Valid @RequestBody FolderRequestDto dto,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success(
                        "New Folder created successfully",
                        folderService.createNewFolder(dto),
                        request.getRequestURI()
                )
        );
    }

    @PutMapping("/{folderId}")
    public ResponseEntity<ApiResponse<FolderResponseDto>> updateExistingFolder(
            @PathVariable @NotBlank(message = "Folder id cannot be empty or null") String folderId,
            @Valid @RequestBody FolderRequestDto dto,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(
                        "Folder updated sucessfully",
                        folderService.updateFolder(folderId, dto),
                        request.getRequestURI()
                )
        );
    }

    @DeleteMapping("/{folderId}")
    public ResponseEntity<ApiResponse<Object>> deleteFolder(
            @PathVariable @NotBlank(message = "Folder id cannot be empty or null") String folderId,
            HttpServletRequest request
    ) {
        folderService.deleteFolder(folderId);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(
                        "Folder deleted successfully",
                        request.getRequestURI()
                )
        );
    }
}

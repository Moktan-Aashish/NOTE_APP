package com.note.backend.controllers;

import com.note.backend.dtos.folder.FolderResponseDto;
import com.note.backend.dtos.response.ApiResponse;
import com.note.backend.services.FolderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/folder")
public class FolderController {

    private final FolderService folderService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<FolderResponseDto>>> getAllFolders(HttpServletRequest request) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Folders fetched successfully",
                        folderService.getAllFolders(),
                        request.getRequestURI()
                )
        );
    }

    
}

package com.note.backend.controllers;

import com.note.backend.dtos.folder.FolderRequestDto;
import com.note.backend.dtos.folder.FolderResponseDto;
import com.note.backend.dtos.note.NoteResponseDto;
import com.note.backend.dtos.response.ApiResponse;
import com.note.backend.services.FolderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Folders", description = "Endpoints for managing the folders")
public class FolderController {

    private final FolderService folderService;

    @Operation(summary = "Fetch all folders")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "List of the folders returned"
    )
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

    @Operation(summary = "Fetch notes of the folder")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "List of notes of the folder returned"
    )
    @GetMapping("/{folderId}")
    public ResponseEntity<ApiResponse<List<NoteResponseDto>>> getFolderNotes(
            @Parameter(description = "Unique Id of the folder")
            @NotBlank(message = "Folder id cannot be empty or null")
            @PathVariable String folderId,
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

    @Operation(
            summary = "Create a new folder",
            description = "Creates a new folder and returns the saved folder"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "New folder created successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Invalid request body provided"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "409",
                    description = "Folder with the same name already exists"
            )
    })
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

    @Operation(
            summary = "Update folder name",
            description = "Updates the name of the folder and returns the updated folder"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Folder updated successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Invalid request body provided"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Could not find the folder for updating"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "409",
                    description = "Folder with the same name already exists"
            )
    })
    @PutMapping("/{folderId}")
    public ResponseEntity<ApiResponse<FolderResponseDto>> updateExistingFolder(
            @Parameter(description = "Unique Id of the folder")
            @NotBlank(message = "Folder id cannot be empty or null")
            @PathVariable String folderId,
            @Valid @RequestBody FolderRequestDto dto,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(
                        "Folder updated successfully",
                        folderService.updateFolder(folderId, dto),
                        request.getRequestURI()
                )
        );
    }

    @Operation(summary = "Delete a folder")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Folder deleted successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Could not find the folder for deleting"
            )
    })
    @DeleteMapping("/{folderId}")
    public ResponseEntity<ApiResponse<Object>> deleteFolder(
            @Parameter(description = "Unique Id of the folder")
            @NotBlank(message = "Folder id cannot be empty or null")
            @PathVariable String folderId,
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

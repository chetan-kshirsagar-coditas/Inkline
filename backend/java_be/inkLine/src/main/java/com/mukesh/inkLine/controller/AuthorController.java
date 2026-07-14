package com.mukesh.inkLine.controller;

import com.mukesh.inkLine.dto.request.EditDraftRequestDTO;
import com.mukesh.inkLine.dto.request.StartNewContentRequestDTO;
import com.mukesh.inkLine.dto.response.GetContentsResponseDTO;
import com.mukesh.inkLine.dto.response.GetDraftsResponseDTO;
import com.mukesh.inkLine.dto.response.StartNewContentResponseDTO;
import com.mukesh.inkLine.global.ApiResponse;
import com.mukesh.inkLine.service.AuthorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/author")
@RequiredArgsConstructor
@Tag(name = "Author related APIs")
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping(value = "/start-content", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<StartNewContentResponseDTO>> startNewContent(
            @RequestBody @Valid StartNewContentRequestDTO request,
            @RequestParam(required = false, name = "file", defaultValue = "null") MultipartFile file
    ) {
        return ApiResponse.success(
                HttpStatus.OK,
                "Started a new content by the author",
                authorService.startNewContent(request, file)
        );
    }

    @GetMapping("/my-drafts")
    public ResponseEntity<ApiResponse<List<GetDraftsResponseDTO>>> getAllDraftsOfAuthor(
            @RequestParam(required = false, name = "page", defaultValue = "0") int page,
            @RequestParam(required = false, name = "size", defaultValue = "5") int size,
            @RequestParam(required = false, name = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(required = false, name = "sortOrder", defaultValue = "ASC") String sortOrder
    ) {
        return ApiResponse.success(
                HttpStatus.OK,
                "Retrieved all the drafts regarding current author",
                authorService.getAllDrafts(page, size, sortBy, sortOrder)
        );
    }

    @GetMapping("/my-content")
    public ResponseEntity<ApiResponse<List<GetContentsResponseDTO>>> getAllContentsOfAuthor(
            @RequestParam(required = false, name = "page", defaultValue = "0") int page,
            @RequestParam(required = false, name = "size", defaultValue = "5") int size,
            @RequestParam(required = false, name = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(required = false, name = "sortOrder", defaultValue = "ASC") String sortOrder
    ) {
        return ApiResponse.success(
                HttpStatus.OK,
                "Retrieved all the content related to the currentUser",
                authorService.getAllContentsOfAuthor(page, size, sortBy, sortOrder)
        );
    }

    @GetMapping("/draft/{draftId}")
    public ResponseEntity<ApiResponse<GetDraftsResponseDTO>> getRequestedDraft(@PathVariable @NotNull UUID draftId) {
        return ApiResponse.success(
                HttpStatus.OK,
                "Retrieved the details of requested Draft",
                authorService.getRequestedDraft(draftId)
        );
    }

    @PostMapping("/add/attachment/{contentId}")
    public ResponseEntity<ApiResponse<String>> uploadAttachment(
            @PathVariable @NotNull UUID contentId,
            @RequestParam(name = "file") @NotNull MultipartFile file
    ) {
        return ApiResponse.success(
                HttpStatus.OK,
                "File upload is successful.",
                authorService.uploadAttachment(contentId, file)
        );
    }

    @PostMapping("/upload/cover-image/{contentId}")
    public ResponseEntity<ApiResponse<String>> uploadCoverImage(
            @PathVariable @NotNull UUID contentId,
            @RequestParam(name = "file") @NotNull MultipartFile file
    ) {
        return ApiResponse.success(
                HttpStatus.OK,
                "Cover Image uploaded successfully",
                authorService.uploadCoverImage(contentId, file)
        );
    }

    @PatchMapping("/submit/draft/{draftId}")
    public ResponseEntity<ApiResponse<String>> submitDraft(@PathVariable @NotNull UUID draftId) {
        return ApiResponse.success(
                HttpStatus.OK,
                "Submitted the requested draft",
                authorService.submitDraft(draftId)
        );
    }

    @GetMapping("/content/{contentId}/status")
    public ResponseEntity<ApiResponse<String>> getContentStatus(@PathVariable @NotNull UUID contentId) {
        return ApiResponse.success(
                HttpStatus.OK,
                "Retrieved the status of the requested content",
                authorService.getContentStatus(contentId)
        );
    }

    @PatchMapping("/edit/draft")
    public ResponseEntity<ApiResponse<String>> editDraft(@RequestBody @Valid EditDraftRequestDTO request) {
        return ApiResponse.success(
                HttpStatus.OK,
                "Requested draft is successfully edited",
                authorService.editDraft(request)
        );
    }
}

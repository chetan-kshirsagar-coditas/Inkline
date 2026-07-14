package com.mukesh.inkLine.controller;

import com.mukesh.inkLine.dto.response.GetDraftsForEditorReviewResponseDTO;
import com.mukesh.inkLine.global.ApiResponse;
import com.mukesh.inkLine.service.EditorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/editor")
@RequiredArgsConstructor
@Tag(name = "Editor related APIs")
public class EditorController {
    private final EditorService editorService;

    @GetMapping("/drafts")
    public ResponseEntity<ApiResponse<List<GetDraftsForEditorReviewResponseDTO>>> getDraftsForEditorReview(
            @RequestParam(required = false, name = "page", defaultValue = "0") int page,
            @RequestParam(required = false, name = "size", defaultValue = "5") int size,
            @RequestParam(required = false, name = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(required = false, name = "sortOrder", defaultValue = "ASC") String sortOrder
    ) {
        return ApiResponse.success(
                HttpStatus.OK,
                "Retrieved all the drafts waiting for editor review.",
                editorService.getDraftsForEditorReview(page, size, sortBy, sortOrder)
        );
    }

    @GetMapping("/draft/{draftId}")
    public ResponseEntity<ApiResponse<GetDraftsForEditorReviewResponseDTO>> getDraftById(@PathVariable @NotNull UUID draftId) {
        return ApiResponse.success(
                HttpStatus.OK,
                "Retrieved the details of the requested Draft",
                editorService.getDraftById(draftId)
        );
    }

    @PatchMapping("/draft/{draftId}/approve")
    public ResponseEntity<ApiResponse<String>> approveDraft(@PathVariable @NotNull UUID draftId) {
        return ApiResponse.success(
                HttpStatus.OK,
                "Approved the requested draft.",
                editorService.approveDraft(draftId)
        );
    }

    @PatchMapping("/draft/{draftId}/reject")
    public ResponseEntity<ApiResponse<String>> rejectDraft(@PathVariable @NotNull UUID draftId) {
        return ApiResponse.success(
                HttpStatus.OK,
                "Rejected the requested draft",
                editorService.rejectDraft(draftId)
        );
    }
}

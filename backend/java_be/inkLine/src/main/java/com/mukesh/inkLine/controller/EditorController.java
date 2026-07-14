package com.mukesh.inkLine.controller;

import com.mukesh.inkLine.dto.request.RejectDraftRequestDTO;
import com.mukesh.inkLine.dto.request.RequestChangesRequestDTO;
import com.mukesh.inkLine.dto.response.GetDraftsForEditorReviewResponseDTO;
import com.mukesh.inkLine.global.ApiResponse;
import com.mukesh.inkLine.service.EditorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/editor")
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

    @PatchMapping("/draft/reject")
    public ResponseEntity<ApiResponse<String>> rejectDraft(@RequestBody @Valid RejectDraftRequestDTO request) {
        return ApiResponse.success(
                HttpStatus.OK,
                "Rejected the requested draft",
                editorService.rejectDraft(request)
        );
    }

    @PatchMapping("/draft/request-changes")
    public ResponseEntity<ApiResponse<String>> requestChangesInDraft(@RequestBody @Valid RequestChangesRequestDTO request) {
        return ApiResponse.success(
                HttpStatus.OK,
                "Requested the author for the changes in the draft.",
                editorService.requestChangesInDraft(request)
        );
    }
}

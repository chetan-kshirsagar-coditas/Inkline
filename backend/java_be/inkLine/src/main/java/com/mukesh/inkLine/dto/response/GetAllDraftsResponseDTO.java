package com.mukesh.inkLine.dto.response;

public record GetAllDraftsResponseDTO(
        String title,
        String body,
        String category,
        String createdAt,
        String contentStatus,
        boolean isSubmitted
) {
}

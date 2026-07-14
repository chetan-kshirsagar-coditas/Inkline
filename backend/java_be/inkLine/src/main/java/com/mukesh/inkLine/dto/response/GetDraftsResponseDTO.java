package com.mukesh.inkLine.dto.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record GetDraftsResponseDTO(
        UUID id,
        String title,
        String body,
        String category,
        String createdAt,
        String contentStatus,
        boolean isSubmitted
) {
}

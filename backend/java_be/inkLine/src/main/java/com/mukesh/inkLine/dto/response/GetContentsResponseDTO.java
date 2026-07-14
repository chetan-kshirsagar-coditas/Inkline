package com.mukesh.inkLine.dto.response;

import lombok.Builder;

@Builder
public record GetContentsResponseDTO(
        String title,
        String body,
        String category,
        String createdAt,
        String submittedAt,
        String contentStatus
) {
}

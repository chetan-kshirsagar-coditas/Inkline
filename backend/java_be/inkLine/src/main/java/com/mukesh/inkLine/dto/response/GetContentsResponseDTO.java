package com.mukesh.inkLine.dto.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record GetContentsResponseDTO(
        UUID id,
        String title,
        String body,
        String category,
        String createdAt,
        String submittedAt,
        String contentStatus
) {
}

package com.mukesh.inkLine.dto.response;

import lombok.Builder;

@Builder
public record GetDraftsForEditorReviewResponseDTO(
        String title,
        String body,
        String recommendations,
        String submittedAt
) {
}

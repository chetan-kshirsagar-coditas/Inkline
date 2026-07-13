package com.mukesh.inkLine.dto.response;

import lombok.Builder;

@Builder
public record StartNewContentResponseDTO(
        String message
) {
}

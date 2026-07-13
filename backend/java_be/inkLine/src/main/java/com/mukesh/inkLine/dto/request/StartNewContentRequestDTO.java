package com.mukesh.inkLine.dto.request;

import jakarta.validation.constraints.NotBlank;

public record StartNewContentRequestDTO(
        @NotBlank
        String title,
        @NotBlank
        String body,
        String category
) {
}

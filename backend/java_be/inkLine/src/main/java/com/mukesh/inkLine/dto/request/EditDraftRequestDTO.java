package com.mukesh.inkLine.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EditDraftRequestDTO(
        @NotNull
        UUID draftId,
        String title,
        String body,
        String category
) {
}

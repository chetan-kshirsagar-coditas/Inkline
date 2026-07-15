package com.mukesh.inkLine.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public record StartNewContentRequestDTO(
        @NotBlank
        String title,
        @NotBlank
        String body,
        String category,
        MultipartFile file
) {
}

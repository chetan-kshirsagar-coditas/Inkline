package com.mukesh.inkLine.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record GetPublishedContentResponseDTO(
        String title,
        String body,
        String category,
        String publishedDate,
        String authorName,
        String coverImage,
        List<String> attachments
) {
}

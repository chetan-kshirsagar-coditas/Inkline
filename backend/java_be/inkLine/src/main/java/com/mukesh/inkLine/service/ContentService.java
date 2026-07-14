package com.mukesh.inkLine.service;

import com.mukesh.inkLine.entities.Content;
import com.mukesh.inkLine.entities.Users;
import com.mukesh.inkLine.enums.ContentStatus;
import com.mukesh.inkLine.exceptions.NotFoundException;
import com.mukesh.inkLine.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContentService {
    private final ContentRepository contentRepository;

    public void saveContent(Content content) {
        contentRepository.save(content);
    }

    public boolean isContentPresent(Users author, String title) {
        return contentRepository.existsByAuthorAndTitle(author, title);
    }

    public Content getContentById(UUID contentId) {
        return contentRepository.findById(contentId)
                .orElseThrow(() -> new NotFoundException("Content with specified ID does not exist."));
    }

    public Page<Content> getContentForEditorReview(Pageable pageable) {
        return contentRepository.findAllByContentAndSubmissionStatus(ContentStatus.UNDER_EDITOR_REVIEW, pageable)
                .orElseThrow(() -> new NotFoundException("No content records are found for Editor Review."));
    }

    public Page<Content> getContentOfCurrentAuthor(Pageable pageable, Users author) {
        return contentRepository.findAllByAuthor(pageable, author)
                .orElseThrow(() -> new NotFoundException("No content records are found for current user."));

    }

    public Page<Content> getPublishedContent(Pageable pageable) {
        return contentRepository.findAllByContentStatus(ContentStatus.APPROVED, pageable)
                .orElseThrow(() -> new NotFoundException("There are no published content yet."));
    }
}

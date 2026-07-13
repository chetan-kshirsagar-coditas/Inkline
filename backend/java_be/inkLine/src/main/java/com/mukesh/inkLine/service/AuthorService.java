package com.mukesh.inkLine.service;

import com.mukesh.inkLine.dto.request.StartNewContentRequestDTO;
import com.mukesh.inkLine.dto.response.StartNewContentResponseDTO;
import com.mukesh.inkLine.entities.Categories;
import com.mukesh.inkLine.entities.Content;
import com.mukesh.inkLine.entities.Drafts;
import com.mukesh.inkLine.entities.Users;
import com.mukesh.inkLine.enums.ContentStatus;
import com.mukesh.inkLine.exceptions.InvalidRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorService {
    private final CommonService commonService;
    private final ContentService contentService;
    private final DraftService draftService;
    private final CategoriesService categoriesService;

    public StartNewContentResponseDTO startNewContent(StartNewContentRequestDTO request) {
        Users currentUser = commonService.getCurrentUser();

        if(contentService.isContentPresent(currentUser, request.title())) throw new InvalidRequestException("A content with the requested title is already present. Please re-verify.");

        Content newContent = Content.builder()
                .author(currentUser)
                .title(request.title())
                .body(request.body())
                .contentStatus(ContentStatus.DRAFT)
                .build();

        if(request.category() != null) {
            Categories requestedCategory = categoriesService.getCategoryByCategoryName(request.category());
            newContent.setCategory(requestedCategory);
            log.info("Added the category after verifying the selected category.");
        }

        contentService.saveContent(newContent);
        log.info("A new content is created by the author: {} {}", currentUser.getFirstName(), currentUser.getLastName());

        Drafts draft = draftService.getDraft(newContent);
        return StartNewContentResponseDTO.builder()
                .message("A draft of the content is created at: " + draft.getCreatedAt().toString())
                .build();
    }
}

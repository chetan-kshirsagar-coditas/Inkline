package com.mukesh.inkLine.service;

import com.mukesh.inkLine.dto.response.GetDraftsForEditorReviewResponseDTO;
import com.mukesh.inkLine.entities.AiRecommendations;
import com.mukesh.inkLine.entities.Content;
import com.mukesh.inkLine.entities.Drafts;
import com.mukesh.inkLine.enums.ContentStatus;
import com.mukesh.inkLine.enums.Roles;
import com.mukesh.inkLine.exceptions.InvalidRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EditorService {
    private final CommonService commonService;
    private final ContentService contentService;
    private final AiRecommendationService aiRecommendationService;
    private final DraftService draftService;

    public List<GetDraftsForEditorReviewResponseDTO> getDraftsForEditorReview(int page, int size, String sortBy, String sortOrder) {
        if(!commonService.getCurrentUser().getRole().equals(Roles.EDITOR)) throw new InvalidRequestException("This feature is only accessible by the EDITOR.");

        Sort sort = sortOrder.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageable = PageRequest.of(page, size, sort);
        Page<Content> contentPage = contentService.getContentForEditorReview(pageable);
        List<GetDraftsForEditorReviewResponseDTO> response = new ArrayList<>();

        for(Content content : contentPage.getContent()) {
            AiRecommendations recommendations = aiRecommendationService.getRecommendations(content);
            GetDraftsForEditorReviewResponseDTO details = GetDraftsForEditorReviewResponseDTO.builder()
                    .title(content.getTitle())
                    .body(content.getBody())
                    .submittedAt(content.getSubmittedAt().toString())
                    .recommendations(recommendations.getSuggestions())
                    .build();
            response.add(details);
        }

        return response;
    }

    public GetDraftsForEditorReviewResponseDTO getDraftById(UUID draftId) {
        Drafts requestedDraft = draftService.getDraftById(draftId);
        AiRecommendations recommendations = aiRecommendationService.getRecommendations(requestedDraft.getContent());
        return GetDraftsForEditorReviewResponseDTO.builder()
                .title(requestedDraft.getContent().getTitle())
                .body(requestedDraft.getContent().getBody())
                .recommendations(recommendations.getSuggestions())
                .submittedAt(requestedDraft.getContent().getSubmittedAt().toString())
                .build();
    }

    public String approveDraft(UUID draftId) {
        Drafts requestedDraft = draftService.getDraftById(draftId);
        requestedDraft.getContent().setContentStatus(ContentStatus.APPROVED);
        draftService.saveDraft(requestedDraft);
        log.info("Changed the status of the requested Draft to APPROVED.");

        return "Successfully approved the requested Draft";
    }

    public String rejectDraft(UUID draftId) {
        Drafts requestedDraft = draftService.getDraftById(draftId);
        requestedDraft.getContent().setContentStatus(ContentStatus.REJECTED);
        draftService.saveDraft(requestedDraft);
        log.info("Changed the status of the requested Draft to REJECTED.");

        return "Successfully rejected the requested Draft";
    }
}

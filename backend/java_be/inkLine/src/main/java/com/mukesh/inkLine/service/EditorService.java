package com.mukesh.inkLine.service;

import com.mukesh.inkLine.dto.request.RejectDraftRequestDTO;
import com.mukesh.inkLine.dto.request.RequestChangesRequestDTO;
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

import java.time.LocalDateTime;
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
    private final MailService mailService;

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
        requestedDraft.getContent().setPublishedAt(LocalDateTime.now());
        draftService.saveDraft(requestedDraft);
        log.info("Changed the status of the requested Draft to APPROVED. And updated the publishedAt date of the content.");

        mailService.sendMail(commonService.getCurrentUser().getEmail(), requestedDraft.getContent().getAuthor().getEmail(), "Content Approved", "Your content of ID: " + requestedDraft.getContent().getId() + " is approved.");
        log.info("Mail is sent to the author regarding approval of submitted content-draft.");

        return "Successfully approved the requested Draft";
    }

    public String rejectDraft(RejectDraftRequestDTO request) {
        Drafts requestedDraft = draftService.getDraftById(request.draftId());
        requestedDraft.getContent().setContentStatus(ContentStatus.REJECTED);
        draftService.saveDraft(requestedDraft);
        log.info("Changed the status of the requested Draft to REJECTED.");

        mailService.sendMail(commonService.getCurrentUser().getEmail(), requestedDraft.getContent().getAuthor().getEmail(), "Content Rejection", "Your content of ID: " + requestedDraft.getContent().getId() + " has been rejected by the Editor.\nReason Of Rejection:\n" + request.reasonOfRejection());
        log.info("Rejection mail is sent to the Author along with the reason of rejection.");

        return "Successfully rejected the requested Draft";
    }

    public String requestChangesInDraft(RequestChangesRequestDTO request) {
        Drafts requestedDraft = draftService.getDraftById(request.draftId());
        requestedDraft.getContent().setContentStatus(ContentStatus.DRAFT);
        requestedDraft.setSubmitted(false);
        draftService.saveDraft(requestedDraft);
        log.info("Marked the requested draft's content-status to 'DRAFT'.");

        mailService.sendMail(commonService.getCurrentUser().getEmail(), requestedDraft.getContent().getAuthor().getEmail(), "Requesting changes in the submitted draft", "Changes Requested in your content of ID: " + requestedDraft.getContent().getId() + " are as follows:\n" + request.requestedChanges());
        log.info("Mail is sent to the author requesting for the suggested changes.");

        return "Successfully marked the content for review and sent mail to the author requesting for suggested changes.";
    }
}

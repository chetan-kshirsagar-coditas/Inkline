package com.mukesh.inkLine.service;

import com.mukesh.inkLine.dto.request.EditDraftRequestDTO;
import com.mukesh.inkLine.dto.request.StartNewContentRequestDTO;
import com.mukesh.inkLine.dto.response.GetContentsResponseDTO;
import com.mukesh.inkLine.dto.response.GetDraftsResponseDTO;
import com.mukesh.inkLine.dto.response.StartNewContentResponseDTO;
import com.mukesh.inkLine.entities.Attachments;
import com.mukesh.inkLine.entities.Categories;
import com.mukesh.inkLine.entities.Content;
import com.mukesh.inkLine.entities.Documents;
import com.mukesh.inkLine.entities.Drafts;
import com.mukesh.inkLine.entities.Users;
import com.mukesh.inkLine.enums.ContentStatus;
import com.mukesh.inkLine.enums.DocumentType;
import com.mukesh.inkLine.enums.Roles;
import com.mukesh.inkLine.exceptions.InvalidRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthorService {
    private final CommonService commonService;
    private final ContentService contentService;
    private final DraftService draftService;
    private final CategoriesService categoriesService;
    private final S3Service s3Service;
    private final AttachmentService attachmentService;
    private final DocumentsService documentsService;

    @Transactional
    public StartNewContentResponseDTO startNewContent(StartNewContentRequestDTO request) {
        Users currentUser = commonService.getCurrentUser();

        if(contentService.isContentPresent(currentUser, request.title())) throw new InvalidRequestException("A content with the requested title is already present. Please re-verify.");

        Content newContent = Content.builder()
                .author(currentUser)
                .title(request.title())
                .body(request.body())
                .contentStatus(ContentStatus.DRAFT)
                .submittedAt(null)
                .build();

        if(request.category() != null) {
            Categories requestedCategory = categoriesService.getCategoryByCategoryName(request.category());
            if(requestedCategory == null) throw new InvalidRequestException("The specified category is not valid.");
            newContent.setCategory(requestedCategory);
            log.info("Added the category after verifying the selected category.");
        }

        contentService.saveContent(newContent);
        log.info("A new content is created by the author: {} {}", currentUser.getFirstName(), currentUser.getLastName());

        String fileName = UUID.randomUUID() + "_" + request.file().getOriginalFilename();
        String key = "content/" + newContent.getId() + "/attachments/" + fileName;
        log.info(s3Service.uploadFile(request.file(), key));

        Attachments newAttachment = Attachments.builder()
                .attachmentPath(key)
                .content(newContent)
                .uploadedAt(LocalDateTime.now())
                .isPublic(true)
                .build();
        attachmentService.saveAttachment(newAttachment);
        log.info("A new entry for the attachment is created successfully.");

        Documents newDocument = Documents.builder()
                .documentType(DocumentType.ATTACHMENT)
                .documentUrl(key)
                .build();
        documentsService.saveDocument(newDocument);
        log.info("Created a new Entry of Document entry.");

        Drafts draft = draftService.getDraft(newContent);
        return StartNewContentResponseDTO.builder()
                .message("A draft of the content is created at: " + draft.getCreatedAt().toString())
                .build();
    }

    public List<GetDraftsResponseDTO> getAllDrafts(int page, int size, String sortBy, String sortOrder) {
        PageRequest pageable = checkUser(page, size, sortBy, sortOrder);
        Page<Drafts> draftsPage = draftService.getAllDrafts(commonService.getCurrentUser(), pageable);
        log.info("Extracted all the drafts related to current user.");

        List<GetDraftsResponseDTO> response = new ArrayList<>();
        for(Drafts drafts : draftsPage.getContent()) {
            Content content = drafts.getContent();
            GetDraftsResponseDTO details = GetDraftsResponseDTO.builder()
                    .id(drafts.getId())
                    .title(content.getTitle())
                    .body(content.getBody())
                    .contentStatus(content.getContentStatus().name())
                    .createdAt(drafts.getCreatedAt().toString())
                    .isSubmitted(drafts.isSubmitted())
                    .category(content.getCategory().getCategoryName())
                    .build();
            response.add(details);
        }

        return response;
    }

    public List<GetContentsResponseDTO> getAllContentsOfAuthor(int page, int size, String sortBy, String sortOrder) {
        PageRequest pageable = checkUser(page, size, sortBy, sortOrder);
        Page<Content> contentPage = contentService.getContentOfCurrentAuthor(pageable, commonService.getCurrentUser());
        log.info("Retrieved the contents related to the current user.");

        List<GetContentsResponseDTO> response = new ArrayList<>();
        for(Content content : contentPage.getContent()) {
            GetContentsResponseDTO details = GetContentsResponseDTO.builder()
                    .id(content.getId())
                    .title(content.getTitle())
                    .body(content.getBody())
                    .submittedAt(content.getSubmittedAt().toString())
                    .category(content.getCategory().getCategoryName())
                    .contentStatus(content.getContentStatus().name())
                    .createdAt(content.getCreatedAt().toString())
                    .build();
            response.add(details);
        }

        return response;
    }

    public GetDraftsResponseDTO getRequestedDraft(UUID id) {
        Users currentUser = commonService.getCurrentUser();
        if(!currentUser.getRole().equals(Roles.AUTHOR)) throw new InvalidRequestException("Current is not an Author.");

        Drafts requestedDraft = draftService.getRequestedDraft(currentUser, id);
        return GetDraftsResponseDTO.builder()
                .id(id)
                .title(requestedDraft.getContent().getTitle())
                .body(requestedDraft.getContent().getBody())
                .category(requestedDraft.getContent().getCategory().getCategoryName())
                .contentStatus(requestedDraft.getContent().getContentStatus().name())
                .createdAt(requestedDraft.getCreatedAt().toString())
                .isSubmitted(requestedDraft.isSubmitted())
                .build();
    }

    public String uploadAttachment(UUID contentId, MultipartFile file) {
        Content content = contentService.getContentById(contentId);
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        String key = "content/" + content.getId() + "/attachments/" + fileName;
        String response =  s3Service.uploadFile(file, key);

        Attachments newAttachment = Attachments.builder().attachmentPath(key).content(content).build();
        attachmentService.saveAttachment(newAttachment);
        log.info("A new attachment for the content of ID: {} is added.", contentId);

        Documents newDocument = Documents.builder().documentType(DocumentType.ATTACHMENT).documentUrl(key).build();
        documentsService.saveDocument(newDocument);
        log.info("A new record for the attachment is created in the documents-entity.");

        return response;
    }

    public String uploadCoverImage(UUID contentId, MultipartFile file) {
        Content content = contentService.getContentById(contentId);
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        String key = "content/" + content.getId() + "/attachments/" + fileName;
        String response = s3Service.uploadFile(file, key);

        Attachments newAttachment = Attachments.builder().content(content).attachmentPath(key).build();
        attachmentService.saveAttachment(newAttachment);
        log.info("A new attachment is created as Cover-Pic of the content of ID: {}", contentId);

        Documents documents = Documents.builder().documentUrl(key).documentType(DocumentType.COVER_PIC).build();
        documentsService.saveDocument(documents);
        log.info("A new document entry is created for the cover-pic of the requested content.");
        return response;
    }

    public String submitDraft(UUID draftId) {
        Drafts requestedDraft = draftService.getRequestedDraft(commonService.getCurrentUser(), draftId);
        requestedDraft.setSubmitted(true);
        requestedDraft.getContent().setContentStatus(ContentStatus.UNDER_AI_REVIEW);
        requestedDraft.getContent().setSubmittedAt(LocalDateTime.now());
        draftService.saveDraft(requestedDraft);

        log.info("Submitted the requested draft for AI review. The content status is changed to: {} and marked the submittedAt time.", requestedDraft.getContent().getContentStatus().name());

        return "Submitted the draft successfully";
    }

    public String getContentStatus(UUID contentId) {
        Content requestedContent = contentService.getContentById(contentId);
        return "The Status of the requested content is: " + requestedContent.getContentStatus().name();
    }

    public PageRequest checkUser(int page, int size, String sortBy, String sortOrder) {
        Users currentUser = commonService.getCurrentUser();
        if(!currentUser.getRole().equals(Roles.AUTHOR)) throw new InvalidRequestException("Current user is not an Author.");

        Sort sort = sortOrder.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        return PageRequest.of(page, size, sort);
    }

    public String editDraft(EditDraftRequestDTO request) {
        Drafts requestedDraft = draftService.getDraftById(request.draftId());
        if(requestedDraft.isSubmitted()) throw new InvalidRequestException("The draft is already submitted. Hence it cannot be edited.");

        Content requestedContent = requestedDraft.getContent();
        if(request.title() != null) requestedContent.setTitle(request.title());
        if(request.body() != null) requestedContent.setBody(request.body());
        if(request.category() != null) {
            Categories requestedCategory = categoriesService.getCategoryByCategoryName(request.category());
            if(requestedCategory == null) throw new InvalidRequestException("The specified category does not exist. Please verify and try again.");
            else requestedContent.setCategory(requestedCategory);
        }
        draftService.saveDraft(requestedDraft);
        log.info("Requested draft is successfully updated.");

        return "The draft with ID: " + requestedDraft.getId() + " is successfully updated.";
    }
}

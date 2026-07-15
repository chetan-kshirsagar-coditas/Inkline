package com.mukesh.inkLine.service;

import com.mukesh.inkLine.dto.response.GetPublishedContentResponseDTO;
import com.mukesh.inkLine.entities.Attachments;
import com.mukesh.inkLine.entities.Categories;
import com.mukesh.inkLine.entities.Content;
import com.mukesh.inkLine.entities.Documents;
import com.mukesh.inkLine.entities.Users;
import com.mukesh.inkLine.enums.DocumentType;
import com.mukesh.inkLine.exceptions.NotFoundException;
import com.mukesh.inkLine.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CommonService {
    private final UsersRepository usersRepository;
    private final CategoryService categoryService;
    private final ContentService contentService;
    private final AttachmentService attachmentService;
    private final DocumentsService documentsService;

    public Users getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return usersRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User trying to access is not validated."));
    }

    public List<String> getAllCategories(int page, int size, String sortBy, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Categories> categoriesPage = categoryService.getAllCategories(pageable);
        log.info("Extracted the available categories");

        List<String> response = new ArrayList<>();
        for(Categories category : categoriesPage.getContent()) {
            response.add(category.getCategoryName());
        }

        return response;
    }

    public List<GetPublishedContentResponseDTO> getAllPublishedContent(int page, int size, String sortBy, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Content> contentPage = contentService.getPublishedContent(pageable);
        log.info("Retrieved the details of the content which are marked published.");

        List<GetPublishedContentResponseDTO> response = new ArrayList<>();
        for(Content content : contentPage.getContent()) {
            List<Attachments> attachmentsList = attachmentService.getAttachments(content);
            List<String> contentAttachments = attachmentsList.stream().map(Attachments::getAttachmentPath).toList();
            GetPublishedContentResponseDTO details = GetPublishedContentResponseDTO.builder()
                    .title(content.getTitle())
                    .body(content.getBody())
                    .category(content.getCategory().getCategoryName())
                    .publishedDate(content.getPublishedAt().toString())
                    .authorName(content.getAuthor().getFirstName() + " " + content.getAuthor().getLastName())
                    .coverImage(findCoverImageUrl(attachmentsList))
                    .attachments(contentAttachments)
                    .build();
            response.add(details);
        }

        return response;
    }

    public String findCoverImageUrl(List<Attachments> attachmentsList) {
        for(Attachments attachment : attachmentsList) {
            Documents document = documentsService.findDocumentByDocumentUrl(attachment.getAttachmentPath());
            if(document.getDocumentType().equals(DocumentType.COVER_PIC)) return document.getDocumentUrl();
        }
        return null;
    }

    public GetPublishedContentResponseDTO getPublishedContent(UUID contentId) {
        Content requestedContent = contentService.getContentById(contentId);
        List<Attachments> attachmentsList = attachmentService.getAttachments(requestedContent);
        String coverImage = findCoverImageUrl(attachmentsList);
        return GetPublishedContentResponseDTO.builder()
                .title(requestedContent.getTitle())
                .body(requestedContent.getBody())
                .category(requestedContent.getCategory().getCategoryName())
                .authorName(requestedContent.getAuthor().getFirstName() + " " + requestedContent.getAuthor().getLastName())
                .publishedDate(requestedContent.getPublishedAt().toString())
                .coverImage(coverImage)
                .attachments(attachmentsList.stream().map(Attachments::getAttachmentPath).toList())
                .build();
    }

}

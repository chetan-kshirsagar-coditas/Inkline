package com.mukesh.inkLine.service;

import com.mukesh.inkLine.entities.Content;
import com.mukesh.inkLine.entities.Drafts;
import com.mukesh.inkLine.entities.Users;
import com.mukesh.inkLine.exceptions.NotFoundException;
import com.mukesh.inkLine.repository.DraftsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DraftService {
    private final DraftsRepository draftsRepository;

    public Drafts getDraft(Content content) {
        if(draftsRepository.existsByContent(content)) return draftsRepository.findByContent(content);

        Drafts newDraft = Drafts.builder()
                .content(content)
                .build();
        draftsRepository.save(newDraft);
        log.info("A new draft is created, for the content: {}", content.getTitle());

        return newDraft;
    }

    public Page<Drafts> getAllDrafts(Users author, Pageable pageable) {
        return draftsRepository.findAllByContent_Author(author, pageable);
    }

    public Drafts getRequestedDraft(Users author, UUID draftId) {
        return draftsRepository.findByIdAndContent_Author(draftId, author)
                .orElseThrow(() -> new NotFoundException("Draft with specified ID is not found."));
    }

    public void saveDraft(Drafts draft) {
        draftsRepository.save(draft);
    }
}

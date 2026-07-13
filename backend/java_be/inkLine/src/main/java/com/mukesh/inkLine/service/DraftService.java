package com.mukesh.inkLine.service;

import com.mukesh.inkLine.entities.Content;
import com.mukesh.inkLine.entities.Drafts;
import com.mukesh.inkLine.entities.Users;
import com.mukesh.inkLine.repository.DraftsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

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

    /*public Page<Drafts> getAllDrafts(Users author) {
        return draftsRepository.findAllByContent_Author
    }*/
}

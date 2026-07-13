package com.mukesh.inkLine.service;

import com.mukesh.inkLine.entities.Content;
import com.mukesh.inkLine.entities.Users;
import com.mukesh.inkLine.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}

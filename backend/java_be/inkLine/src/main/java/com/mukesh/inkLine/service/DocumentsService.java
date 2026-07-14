package com.mukesh.inkLine.service;

import com.mukesh.inkLine.entities.Documents;
import com.mukesh.inkLine.repository.DocumentsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentsService {
    private final DocumentsRepository documentsRepository;

    public void saveDocument(Documents document) {
        documentsRepository.save(document);
    }

    public Documents findDocumentByDocumentUrl(String documentUrl) {
        return documentsRepository.findByDocumentUrl(documentUrl);
    }
}

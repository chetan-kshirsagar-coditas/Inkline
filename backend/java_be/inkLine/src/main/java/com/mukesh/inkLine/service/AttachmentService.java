package com.mukesh.inkLine.service;

import com.mukesh.inkLine.entities.Attachments;
import com.mukesh.inkLine.repository.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttachmentService {
    private final AttachmentRepository attachmentRepository;

    public void saveAttachment(Attachments attachment) {
        attachmentRepository.save(attachment);
    }
}

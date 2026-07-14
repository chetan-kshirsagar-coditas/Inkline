package com.mukesh.inkLine.repository;

import com.mukesh.inkLine.entities.Attachments;
import com.mukesh.inkLine.entities.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachments, UUID> {
    List<Attachments> findAllByContent(Content content);
}

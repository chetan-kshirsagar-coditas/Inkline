package com.mukesh.inkLine.repository;

import com.mukesh.inkLine.entities.Documents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DocumentsRepository extends JpaRepository<Documents, UUID> {
    Documents findByDocumentUrl(String documentUrl);
}

package com.mukesh.inkLine.repository;

import com.mukesh.inkLine.entities.Content;
import com.mukesh.inkLine.entities.Users;
import com.mukesh.inkLine.enums.ContentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContentRepository extends JpaRepository<Content, UUID> {
    boolean existsByAuthorAndTitle(Users author, String title);

    Optional<Content> findById(UUID id);

    Optional<Page<Content>> findAllByAuthor(Pageable pageable, Users author);

    @Query("SELECT c FROM Content c INNER JOIN Drafts d ON d.content.id = c.id WHERE d.isSubmitted = true AND c.contentStatus=:contentStatus")
    public Optional<Page<Content>> findAllByContentAndSubmissionStatus(ContentStatus contentStatus, Pageable pageable);


    Optional<Page<Content>> findAllByContentStatus(ContentStatus contentStatus, Pageable pageable);
}

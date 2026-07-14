package com.mukesh.inkLine.repository;

import com.mukesh.inkLine.entities.Content;
import com.mukesh.inkLine.entities.Drafts;
import com.mukesh.inkLine.entities.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DraftsRepository extends JpaRepository<Drafts, Long> {
    boolean existsByContent(Content content);

    Drafts findByContent(Content content);

    Page<Drafts> findAllByContent_Author(Users author, Pageable pageable);

    Optional<Drafts> findByIdAndContent_Author(UUID id, Users author);
}

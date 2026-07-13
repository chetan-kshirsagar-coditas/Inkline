package com.mukesh.inkLine.repository;

import com.mukesh.inkLine.entities.Content;
import com.mukesh.inkLine.entities.Drafts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DraftsRepository extends JpaRepository<Drafts, Long> {
    boolean existsByContent(Content content);

    Drafts findByContent(Content content);
}

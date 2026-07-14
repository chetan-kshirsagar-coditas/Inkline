package com.mukesh.inkLine.repository;

import com.mukesh.inkLine.entities.Content;
import com.mukesh.inkLine.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {
    boolean existsByAuthorAndTitle(Users author, String title);

    Optional<Content> findById(UUID id);
}

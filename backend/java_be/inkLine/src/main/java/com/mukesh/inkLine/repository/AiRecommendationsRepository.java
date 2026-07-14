package com.mukesh.inkLine.repository;

import com.mukesh.inkLine.entities.AiRecommendations;
import com.mukesh.inkLine.entities.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AiRecommendationsRepository extends JpaRepository<AiRecommendations, UUID> {
    List<AiRecommendations> findAllByContent(Content content);

    @Query("SELECT r FROM AiRecommendations r WHERE r.content.id = :contentId ORDER BY r.createdAt DESC LIMIT 1")
    Optional<AiRecommendations> findLatestRecommendation(UUID contentId);
}

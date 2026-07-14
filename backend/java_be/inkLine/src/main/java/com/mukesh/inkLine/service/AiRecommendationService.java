package com.mukesh.inkLine.service;

import com.mukesh.inkLine.entities.AiRecommendations;
import com.mukesh.inkLine.entities.Content;
import com.mukesh.inkLine.exceptions.NotFoundException;
import com.mukesh.inkLine.repository.AiRecommendationsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiRecommendationService {
    private final AiRecommendationsRepository aiRecommendationsRepository;

    public AiRecommendations getRecommendations(Content content) {
        return aiRecommendationsRepository.findLatestRecommendation(content.getId()).orElseThrow(() -> new NotFoundException("Latest Recommendation for the requested content is not found."));
    }
}

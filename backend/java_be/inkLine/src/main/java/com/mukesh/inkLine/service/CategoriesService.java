package com.mukesh.inkLine.service;

import com.mukesh.inkLine.entities.Categories;
import com.mukesh.inkLine.repository.CategoriesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoriesService {
    private final CategoriesRepository categoriesRepository;

    public Categories getCategoryByCategoryName(String categoryName) {
        return categoriesRepository.findByCategoryName(categoryName).orElse(null);
    }
}

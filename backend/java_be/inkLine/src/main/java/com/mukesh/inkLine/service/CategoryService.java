package com.mukesh.inkLine.service;

import com.mukesh.inkLine.entities.Categories;
import com.mukesh.inkLine.exceptions.NotFoundException;
import com.mukesh.inkLine.repository.CategoriesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
    private final CategoriesRepository categoriesRepository;

    public boolean checkCategoryExistence(String categoryName) {
        return categoriesRepository.existsByCategoryName(categoryName);
    }

    public void saveCategory(Categories categories) {
        categoriesRepository.save(categories);
    }

    public Page<Categories> getAllCategories(Pageable pageable) {
        return categoriesRepository.findAll(pageable);
    }
}

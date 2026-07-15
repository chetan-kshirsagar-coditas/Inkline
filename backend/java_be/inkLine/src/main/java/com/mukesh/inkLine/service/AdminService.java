package com.mukesh.inkLine.service;

import com.mukesh.inkLine.entities.Categories;
import com.mukesh.inkLine.exceptions.InvalidRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AdminService {
    private final CategoryService categoryService;
    private final CommonService commonService;

    public String createCategory(String categoryName) {
        if(categoryService.checkCategoryExistence(categoryName)) throw new InvalidRequestException("Category with specified name already exists.");
        Categories newCategory = Categories.builder()
                .createdBy(commonService.getCurrentUser())
                .categoryName(categoryName)
                .build();
        categoryService.saveCategory(newCategory);
        log.info("A new category is successfully created.");

        return "A new category is created successfully.";
    }
}

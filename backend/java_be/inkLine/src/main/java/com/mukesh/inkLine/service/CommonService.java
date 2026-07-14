package com.mukesh.inkLine.service;

import com.mukesh.inkLine.entities.Categories;
import com.mukesh.inkLine.entities.Users;
import com.mukesh.inkLine.exceptions.NotFoundException;
import com.mukesh.inkLine.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommonService {
    private final UsersRepository usersRepository;
    private final CategoryService categoryService;

    public Users getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return usersRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User trying to access is not validated."));
    }

    public List<String> getAllCategories(int page, int size, String sortBy, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Categories> categoriesPage = categoryService.getAllCategories(pageable);
        log.info("Extracted the available categories");

        List<String> response = new ArrayList<>();
        for(Categories category : categoriesPage.getContent()) {
            response.add(category.getCategoryName());
        }

        return response;
    }
}

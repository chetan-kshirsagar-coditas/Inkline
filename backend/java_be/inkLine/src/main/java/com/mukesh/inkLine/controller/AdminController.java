package com.mukesh.inkLine.controller;

import com.mukesh.inkLine.global.ApiResponse;
import com.mukesh.inkLine.service.AdminService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@Tag(name = "Admin related APIs")
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/create/category/{categoryName}")
    public ResponseEntity<ApiResponse<String>> createCategory(@PathVariable @NotNull String categoryName) {
        return ApiResponse.success(
                HttpStatus.CREATED,
                "Created a new Category",
                adminService.createCategory(categoryName)
        );
    }

}

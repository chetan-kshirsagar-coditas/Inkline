package com.mukesh.inkLine.controller;

import com.mukesh.inkLine.dto.response.GetPublishedContentResponseDTO;
import com.mukesh.inkLine.global.ApiResponse;
import com.mukesh.inkLine.service.CommonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/common")
@RequiredArgsConstructor
@Tag(name = "Common APIs")
public class CommonController {
    private final CommonService commonService;

    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<String>>> getAllCategories(
            @RequestParam(required = false, name = "page", defaultValue = "0") int page,
            @RequestParam(required = false, name = "size", defaultValue = "5") int size,
            @RequestParam(required = false, name = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(required = false, name = "sortOrder", defaultValue = "ASC") String sortOrder
    ) {
        return ApiResponse.success(
                HttpStatus.OK,
                "Retrieved all the categories.",
                commonService.getAllCategories(page, size, sortBy, sortOrder)
        );
    }

    @GetMapping("/published-content")
    public ResponseEntity<ApiResponse<List<GetPublishedContentResponseDTO>>> getPublishedContent(
            @RequestParam(required = false, name = "page", defaultValue = "0") int page,
            @RequestParam(required = false, name = "size", defaultValue = "5") int size,
            @RequestParam(required = false, name = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(required = false, name = "sortOrder", defaultValue = "ASC") String sortOrder
    ) {
        return ApiResponse.success(
                HttpStatus.OK,
                "Retrieved all the published content.",
                commonService.getPublishedContent(page, size, sortBy, sortOrder)
        );
    }
}

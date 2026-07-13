package com.mukesh.inkLine.controller;

import com.mukesh.inkLine.dto.request.StartNewContentRequestDTO;
import com.mukesh.inkLine.dto.response.StartNewContentResponseDTO;
import com.mukesh.inkLine.global.ApiResponse;
import com.mukesh.inkLine.service.AuthorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/author")
@RequiredArgsConstructor
@Tag(name = "Author related APIs")
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping("/start-content")
    public ResponseEntity<ApiResponse<StartNewContentResponseDTO>> startNewContent(@RequestBody @Valid StartNewContentRequestDTO request) {
        return ApiResponse.success(
                HttpStatus.OK,
                "Started a new content by the author",
                authorService.startNewContent(request)
        );
    }
}

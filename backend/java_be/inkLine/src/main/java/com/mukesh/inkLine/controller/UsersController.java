package com.mukesh.inkLine.controller;

import com.mukesh.inkLine.service.UsersService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/users")
@RequiredArgsConstructor
@Tag(name = "Users related APIs")
public class UsersController {
    private final UsersService usersService;

    @PostMapping("/add")
    public String insertUser() {
        return usersService.saveUser();
    }
}

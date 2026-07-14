package com.mukesh.inkLine.dto.request;

public record AddUserRequestDTO(
        String email,
        String firstName,
        String lastName,
        String role
) {
}

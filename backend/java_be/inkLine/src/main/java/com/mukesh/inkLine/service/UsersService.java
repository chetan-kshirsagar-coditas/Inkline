package com.mukesh.inkLine.service;

import com.mukesh.inkLine.dto.request.AddUserRequestDTO;
import com.mukesh.inkLine.entities.Users;
import com.mukesh.inkLine.enums.Roles;
import com.mukesh.inkLine.exceptions.InvalidRequestException;
import com.mukesh.inkLine.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersService {
    private final UsersRepository usersRepository;

    public String saveUser() {
        Users currentUser = Users.builder()
                .email("mukesh@gmail.com")
                .firstName("mukesh")
                .lastName("pyla")
                .createdBy(new Users())
                .role(Roles.AUTHOR)
                .build();
        usersRepository.save(currentUser);

        return "User saved successfully.";
    }
}

package com.mukesh.inkLine.service;

import com.mukesh.inkLine.entities.Users;
import com.mukesh.inkLine.exceptions.NotFoundException;
import com.mukesh.inkLine.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommonService {
    private final UsersRepository usersRepository;

    public Users getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return usersRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User trying to access is not validated."));
    }
}

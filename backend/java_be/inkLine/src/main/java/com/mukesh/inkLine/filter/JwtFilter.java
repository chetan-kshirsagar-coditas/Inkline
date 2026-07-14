package com.mukesh.inkLine.filter;


import com.mukesh.inkLine.entities.Users;
import com.mukesh.inkLine.exceptions.InvalidRequestException;
import com.mukesh.inkLine.exceptions.NotFoundException;
import com.mukesh.inkLine.repository.UsersRepository;
import com.mukesh.inkLine.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UsersRepository usersRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        String token = null;
        String email = null;

        if(header != null) {
            if(header.startsWith("Bearer ")) {
                token = header.substring(7);
                email = jwtUtil.extractEmail(token);
            }
        }

        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if(jwtUtil.isTokenValid(token)) {
                Users requestedUserDetails = usersRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User with specified email is not found."));
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(requestedUserDetails.getEmail(), null, requestedUserDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                log.info("Security Context Holder is successfully populated with current UserDetails.");
            }
            else {
                throw new InvalidRequestException("AccessToken provided is expired. So, please use your refresh-token or re-login into the application.");
            }
        }

        filterChain.doFilter(request, response);
    }
}


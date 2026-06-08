package com.example.leadforge.security;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import tools.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public CustomAuthenticationEntryPoint(ObjectMapper objectMapper)
    {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException
    {
        response.setStatus(
            HttpServletResponse.SC_UNAUTHORIZED
        );
        
        response.setContentType(
                MediaType.APPLICATION_JSON_VALUE
        );

        Map<String, Object> error =
                new HashMap<>();

        error.put("status", 401);

        error.put(
                "message",
                "Authentication required"
        );

        error.put(
                "timestamp",
                LocalDateTime.now()
        );

        response.getWriter().write(
                objectMapper.writeValueAsString(error)
        );
    }
}

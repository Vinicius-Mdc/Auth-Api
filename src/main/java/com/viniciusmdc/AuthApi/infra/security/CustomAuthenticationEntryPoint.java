package com.viniciusmdc.AuthApi.infra.security;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException {

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", ex.getCause() instanceof ResponseStatusException ?
                ((ResponseStatusException) ex.getCause()).getReason() : "Autenticação inválida.");
        responseBody.put("status", HttpStatus.UNAUTHORIZED.value());
        responseBody.put("error", HttpStatus.UNAUTHORIZED);
        responseBody.put("timestamp", LocalDateTime.now().toString());

        Gson gson = new Gson();
        String jsonResponseBody = gson.toJson(responseBody);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponseBody);
    }
}

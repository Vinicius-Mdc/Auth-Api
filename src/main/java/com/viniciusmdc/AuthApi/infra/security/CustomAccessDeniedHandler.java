package com.viniciusmdc.AuthApi.infra.security;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex) throws IOException {

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", ex.getCause() instanceof ResponseStatusException ?
                ((ResponseStatusException) ex.getCause()).getReason() : "Acesso negado");
        responseBody.put("status", HttpStatus.FORBIDDEN.value());
        responseBody.put("error", HttpStatus.FORBIDDEN);
        responseBody.put("timestamp", LocalDateTime.now().toString());

        Gson gson = new Gson();
        String jsonResponseBody = gson.toJson(responseBody);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponseBody);
    }
}

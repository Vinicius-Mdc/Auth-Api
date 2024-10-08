package com.viniciusmdc.AuthApi.controller;


import com.viniciusmdc.AuthApi.domain.Usuario;
import com.viniciusmdc.AuthApi.dto.AuthenticationDTO;
import com.viniciusmdc.AuthApi.dto.AuthenticationTokenDTO;
import com.viniciusmdc.AuthApi.service.RefreshTokenService;
import com.viniciusmdc.AuthApi.service.TokenService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação")
public class AuthenticationController {

    @Autowired
    TokenService tokenService;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationTokenDTO> login(@RequestBody @Valid AuthenticationDTO credentials) {

        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(credentials.login(), credentials.senha());
        try {
            Authentication auth = this.authenticationManager.authenticate(usernamePassword);
            Usuario usuario = (Usuario) auth.getPrincipal();
            String refreshToken = refreshTokenService.createRefreshToken(usuario);
            String token = tokenService.gerarAccessToken(usuario);
            return ResponseEntity.ok(new AuthenticationTokenDTO(token, refreshToken));
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário ou senha incorretos.");
        }

    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationTokenDTO> refreshToken(@RequestHeader("Authorization") @Parameter(hidden = true) String authorization) {
        AuthenticationTokenDTO tokenDTO = tokenService.refreshAccessToken(authorization);
        return new ResponseEntity<>(tokenDTO, HttpStatus.OK);
    }

}

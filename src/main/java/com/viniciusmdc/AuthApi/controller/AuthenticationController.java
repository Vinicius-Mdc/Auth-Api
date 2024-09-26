package com.viniciusmdc.AuthApi.controller;


import com.viniciusmdc.AuthApi.domain.Usuario;
import com.viniciusmdc.AuthApi.dto.AuthenticationDTO;
import com.viniciusmdc.AuthApi.dto.AuthenticationTokenDTO;
import com.viniciusmdc.AuthApi.service.AuthenticationTokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação")
public class AuthenticationController {

    @Autowired
    AuthenticationTokenService tokenService;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationTokenDTO> login(@RequestBody @Valid AuthenticationDTO credentials){

        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(credentials.login(), credentials.senha());
        try{
            Authentication auth = this.authenticationManager.authenticate(usernamePassword);
            String token = tokenService.gerarAccessToken((Usuario) auth.getPrincipal());
            return ResponseEntity.ok(new AuthenticationTokenDTO(token));
        } catch (AuthenticationException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário ou senha incorretos.");
        }

    }


}

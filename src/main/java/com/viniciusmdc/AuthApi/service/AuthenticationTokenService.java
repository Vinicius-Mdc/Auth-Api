package com.viniciusmdc.AuthApi.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.viniciusmdc.AuthApi.domain.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class AuthenticationTokenService {

    private static final Integer HORAS_EXPIRACAO_ACCESS_TOKEN = 1;
    public static final String ISSUER = "auth-api";
    public static final String OFFSET_BRASILIA = "-03:00";

    @Value("${api.security.token.secret}")
    private String secret;


    public String gerarAccessToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT
                    .create()
                    .withIssuer(ISSUER)
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(getDataExpiracao(HORAS_EXPIRACAO_ACCESS_TOKEN))
                    .sign(algorithm);

        } catch (JWTCreationException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu uma falha ao gerar token de acesso.", e);
        }
    }

    public String validarAccessToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu uma falha ao validar o token de acesso.", e);
        }
    }

    private Instant getDataExpiracao(Integer horas) {
        return LocalDateTime.now().plusHours(horas).toInstant(ZoneOffset.of(OFFSET_BRASILIA));
    }

}

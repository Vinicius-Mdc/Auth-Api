package com.viniciusmdc.AuthApi.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.viniciusmdc.AuthApi.domain.RefreshToken;
import com.viniciusmdc.AuthApi.domain.Usuario;
import com.viniciusmdc.AuthApi.dto.AuthenticationTokenDTO;
import com.viniciusmdc.AuthApi.enums.TipoTokenJwtEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;

@Service
public class TokenService {

    public static final String ISSUER = "auth-api";
    public static final String OFFSET_BRASILIA = "-03:00";

    private static final Integer HORAS_EXPIRACAO_ACCESS_TOKEN = 1;
    private static final Integer HORAS_EXPIRACAO_REFRESH_TOKEN = 24 * 7;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarAccessToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT
                    .create()
                    .withIssuer(ISSUER)
                    .withClaim("type", TipoTokenJwtEnum.ACCESS.getTipoToken())
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(getDataExpiracao(HORAS_EXPIRACAO_ACCESS_TOKEN))
                    .sign(algorithm);

        } catch (JWTCreationException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu uma falha ao gerar token de acesso.", e);
        }
    }

    public String gerarRefreshToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT
                    .create()
                    .withIssuer(ISSUER)
                    .withSubject(usuario.getLogin())
                    .withClaim("type", TipoTokenJwtEnum.REFRESH.getTipoToken())
                    .withClaim("id", UUID.randomUUID().toString())
                    .withExpiresAt(getDataExpiracao(HORAS_EXPIRACAO_REFRESH_TOKEN))
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu uma falha ao gerar token de atualização.", e);
        }
    }

    public String validarToken(String token, TipoTokenJwtEnum tipoToken) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .withClaim("type", tipoToken.getTipoToken())
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Token inválido.", e);
        }
    }

    public Instant getDataExpiracao(Integer horas) {
        return LocalDateTime.now().plusHours(horas).toInstant(ZoneOffset.of(OFFSET_BRASILIA));
    }

    public AuthenticationTokenDTO refreshAccessToken(String authorization) {
        if(authorization != null){
            String rawToken = authorization.replace("Bearer ", "");
            String login = validarToken(rawToken, TipoTokenJwtEnum.REFRESH);
            Usuario usuario = usuarioService.findUsuarioByLogin(login);

            RefreshToken dbToken = refreshTokenService.findById(getTokenId(rawToken));

            if(dbToken == null){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Token não registrado no servidor.");
            }

            return new AuthenticationTokenDTO(gerarAccessToken(usuario), rawToken);

        } else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Token de autorização necessária.");
        }
    }

    public Date getDataExpiracaoToken(String jwt){
        return JWT.decode(jwt).getExpiresAt();
    }

    public UUID getTokenId(String jwt) {
        return JWT.decode(jwt).getClaim("id").as(UUID.class);
    }
}

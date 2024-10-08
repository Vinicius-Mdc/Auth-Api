package com.viniciusmdc.AuthApi.service;

import com.viniciusmdc.AuthApi.domain.RefreshToken;
import com.viniciusmdc.AuthApi.domain.Usuario;
import com.viniciusmdc.AuthApi.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired @Lazy
    TokenService tokenService;

    @Transactional
    public String createRefreshToken(Usuario usuario){
        String token = tokenService.gerarRefreshToken(usuario);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setId(tokenService.getTokenId(token));
        refreshToken.setExpiracao(tokenService.getDataExpiracaoToken(token));
        refreshToken.setUsuario(usuario);

        refreshTokenRepository.save(refreshToken);

        return token;
    }

    public RefreshToken findById(UUID id){
        return refreshTokenRepository.findFirstById(id);
    }

}

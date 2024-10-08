package com.viniciusmdc.AuthApi.repository;

import com.viniciusmdc.AuthApi.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    RefreshToken findFirstById(UUID id);
}

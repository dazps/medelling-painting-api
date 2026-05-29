package com.medellinpainting.medellingpaintingapi.services;

import com.medellinpainting.medellingpaintingapi.entities.RefreshToken;
import com.medellinpainting.medellingpaintingapi.repositories.IRefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private static final long REFRESH_VALIDITY_DAYS = 7;

    @Autowired
    private IRefreshTokenRepository repo;

    public RefreshToken create(String username) {
        repo.deleteByUsername(username);
        RefreshToken rt = new RefreshToken();
        rt.setToken(UUID.randomUUID().toString());
        rt.setUsername(username);
        rt.setExpiresAt(Instant.now().plus(REFRESH_VALIDITY_DAYS, ChronoUnit.DAYS));
        return repo.save(rt);
    }

    public Optional<RefreshToken> validate(String token) {
        return repo.findByToken(token)
                .filter(rt -> rt.getExpiresAt().isAfter(Instant.now()));
    }

    public void deleteByUsername(String username) {
        repo.deleteByUsername(username);
    }
}

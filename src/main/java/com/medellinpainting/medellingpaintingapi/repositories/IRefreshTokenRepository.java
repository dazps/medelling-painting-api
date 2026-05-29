package com.medellinpainting.medellingpaintingapi.repositories;

import com.medellinpainting.medellingpaintingapi.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface IRefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    @Transactional
    void deleteByUsername(String username);
}

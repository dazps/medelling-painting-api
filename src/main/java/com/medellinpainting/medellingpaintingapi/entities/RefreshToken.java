package com.medellinpainting.medellingpaintingapi.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
@Entity
@Table(name = "refresh_tokens")
public class RefreshToken implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 64)
    private String token;

    @Column(nullable = false, length = 30)
    private String username;

    @Column(nullable = false)
    private Instant expiresAt;
}

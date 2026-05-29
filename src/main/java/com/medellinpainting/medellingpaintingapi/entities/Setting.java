package com.medellinpainting.medellingpaintingapi.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "settings")
public class Setting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, unique = true, nullable = false)
    private String key;

    @Column(columnDefinition = "TEXT")
    private String value;
}

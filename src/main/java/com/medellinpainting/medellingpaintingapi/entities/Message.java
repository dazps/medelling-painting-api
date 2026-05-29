package com.medellinpainting.medellingpaintingapi.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 20)
    private String phone;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private LocalDateTime sentAt = LocalDateTime.now();

    private Boolean read = false;
}

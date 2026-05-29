package com.medellinpainting.medellingpaintingapi.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "roles", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "rol"})})
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rol;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}

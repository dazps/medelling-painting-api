package com.medellinpainting.medellingpaintingapi.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 50)
    private String category;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDate date;

    @Column(length = 100)
    private String location;

    @Column(length = 1000)
    private String imageUrl;

    @Column(columnDefinition = "TEXT")
    private String videoUrl;

    private Boolean published = true;

    private Boolean featured = false;

    @Column(length = 20)
    private String surface;

    @Column(length = 50)
    private String duration;

}

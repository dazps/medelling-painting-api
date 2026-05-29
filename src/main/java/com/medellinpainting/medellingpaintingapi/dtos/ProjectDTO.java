package com.medellinpainting.medellingpaintingapi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjectDTO {
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must be 200 characters or less")
    private String title;

    @NotBlank(message = "Category is required")
    private String category;

    @Size(max = 2000)
    private String description;

    private LocalDate date;

    @NotBlank(message = "Location is required")
    @Size(max = 150)
    private String location;

    private String imageUrl;
    private String videoUrl;
    private Boolean published;
    private Boolean featured;
    private String surface;
    private String duration;
}

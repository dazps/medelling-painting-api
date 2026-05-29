package com.medellinpainting.medellingpaintingapi.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDTO {
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be 100 characters or less")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email address")
    @Size(max = 100)
    private String email;

    @Size(max = 20, message = "Phone must be 20 characters or less")
    private String phone;

    @NotBlank(message = "Message content is required")
    private String content;

    private LocalDateTime sentAt;
    private Boolean read;
}

package com.medellinpainting.medellingpaintingapi.dtos;

import lombok.Data;

@Data
public class JwtRequestDTO {
    private String username;
    private String password;
}

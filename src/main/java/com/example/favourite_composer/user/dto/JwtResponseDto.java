package com.example.favourite_composer.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponseDto {
    private String accessToken;
    private String tokenType; // "Bearer"
    private Long id;
    private String nombre;
    private String rol;
}

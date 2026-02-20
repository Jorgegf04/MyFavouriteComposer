package com.example.favourite_composer.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

//LoginDTO with the data the user uses to identify themselves in the app
@Getter
@Setter
public class LoginDto {
    @NotBlank
    private String nombre;

    @NotBlank
    private String password;
}

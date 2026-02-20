package com.example.favourite_composer.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

//SignupDto with user registration data
@Getter
@Setter
public class SignupDto {
    @NotBlank
    @Size(min = 3, max = 20)
    private String name;

    @NotBlank
    private String rol;

    @NotBlank
    @Size(min = 1, max = 40)
    private String password;

}
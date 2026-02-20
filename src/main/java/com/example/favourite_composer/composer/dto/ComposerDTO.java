package com.example.favourite_composer.composer.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ComposerDTO {
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must not exceed 255 characters")
    private String name;

    @Size(max = 255, message = "Artistic name must not exceed 255 characters")
    private String artisticName;

    @Size(max = 2000, message = "Bio must not exceed 2000 characters")
    private String bio;

    private LocalDate birth;
    private LocalDate death;

    @Size(max = 255, message = "Place of birth must not exceed 255 characters")
    private String placeOfBirth;

    @Size(max = 255, message = "Place of death must not exceed 255 characters")
    private String placeOfDeath;

    @Size(max = 255, message = "Nationality must not exceed 255 characters")
    private String nationality;
}

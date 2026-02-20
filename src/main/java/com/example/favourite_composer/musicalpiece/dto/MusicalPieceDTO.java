package com.example.favourite_composer.musicalpiece.dto;

import com.example.favourite_composer.composer.domain.Composer;
import com.example.favourite_composer.musicalpiece.domain.Instrumentation;

import jakarta.validation.constraints.*;

import lombok.Data;

@Data
public class MusicalPieceDTO {
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    @Size(max = 255, message = "Alternative title must not exceed 255 characters")
    private String alternativeTitle;

    @NotNull(message = "Instrumentation is required")
    private Instrumentation instrumentation;

    @Size(max = 255, message = "Premiere must not exceed 255 characters")
    private String premiere;

    @Size(max = 500, message = "Link must no exceed 500 characters")
    private String link;

    @NotNull(message = "Composer is required")
    private Long composerId;
    private String composerName;

}

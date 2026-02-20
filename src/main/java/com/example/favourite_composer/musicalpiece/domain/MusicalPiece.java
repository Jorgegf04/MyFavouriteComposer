package com.example.favourite_composer.musicalpiece.domain;

import org.springframework.hateoas.RepresentationModel;

import com.example.favourite_composer.composer.domain.Composer;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity that represents a musical piece written by a composer.
 */
@Entity
@Table(name = "musical_piece")
@Getter
@Setter
@NoArgsConstructor
public class MusicalPiece extends RepresentationModel<Composer> {
    /**
     * Primary key of the musical piece.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Title of the musical piece.
     * Required field.
     */
    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    @Column(name = "title", nullable = false)
    private String title;

    /**
     * Alternative title (optional).
     */
    @Size(max = 255, message = "Alternative title must not exceed 255 characters")
    @Column(name = "alternative_title")
    private String alternativeTitle;

    /**
     * Instrumentation of the musical piece.
     * Stored as a String in the database.
     */

    @NotNull(message = "Instrumentation is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "instrumentation", nullable = false)
    private Instrumentation instrumentation;

    /**
     * Premiere information (optional).
     */
    @Size(max = 255, message = "Premiere must not exceed 255 characters")
    @Column(name = "premiere")
    private String premiere;

    /**
     * External link to the musical piece (optional).
     */
    @Size(max = 500, message = "Link must not exceed 500 characters")
    @Column(name = "link")
    private String link;

    /**
     * Composer who wrote the musical piece.
     * Owning side of the relationship.
     */
    @NotNull(message = "Composer is required")
    @ManyToOne
    @JoinColumn(name = "composer_id", nullable = false, foreignKey = @ForeignKey(name = "fk_musicalpiece_composer"))
    @JsonIgnore
    private Composer composer;
}

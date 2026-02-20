package com.example.favourite_composer.photocomposer.domain;

import com.example.favourite_composer.composer.domain.Composer;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity that represents a profile photo of a composer.
 */
@Entity
@Table(name = "photo_composer")
@Getter
@Setter
@NoArgsConstructor
public class PhotoComposer {
    /**
     * Primary key of the photo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Link to the photo.
     */
    @NotBlank(message = "Photo link is required")
    @Size(max = 500, message = "Photo link must not exceed 500 characters")
    @Column(name = "link", nullable = false)
    private String link;

    /**
     * Composer associated with this photo.
     * Owning side of the OneToOne relationship.
     */
    @NotNull(message = "Composer is required")
    @OneToOne
    @JoinColumn(name = "composer_id", nullable = false, unique = true, foreignKey = @ForeignKey(name = "fk_photo_composer_composer"))
    @JsonIgnore
    private Composer composer;
}

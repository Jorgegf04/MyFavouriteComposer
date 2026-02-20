package com.example.favourite_composer.composer.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.aot.generate.Generated;
import org.springframework.cglib.core.Local;
import org.springframework.hateoas.RepresentationModel;

import com.example.favourite_composer.musicalpiece.domain.MusicalPiece;
import com.example.favourite_composer.photocomposer.domain.PhotoComposer;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Entity that represents a Composer.
 * A composer can write many musical pieces and has one profile photo.
 */
@Entity
@Table(name = "composer")
@Data
@NoArgsConstructor

public class Composer extends RepresentationModel<Composer> {

    /**
     * Primary key of the composer.
     * Generated automatically by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Real name of the composer */
    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must not exceed 255 characters")
    @Column(name = "name")
    private String name;

    /**
     * Artistic name (optional).
     */
    @Size(max = 255, message = "Artistic name must not exceed 255 characters")
    @Column(name = "artistic_name")
    private String artisticName;

    /** Short biography of the composer */
    @Column(name = "bio", length = 2000)
    private String bio;

    /** Birth date */
    // @Temporal(TemporalTyme.DATE);
    @Column(name = "birth")
    private LocalDate birth;

    /** Death date (optional) */
    @Column(name = "death")
    private LocalDate death;

    /** Place of birth */
    @Size(max = 255, message = "Place of birth must not exceed 255 characters")
    @Column(name = "place_of_birth")
    private String placeOfBirth;

    /** Place of death (optional) */
    @Size(max = 255, message = "Place of death must not exceed 255 characters")
    @Column(name = "place_of_death")
    private String placeOfDeath;

    /** Nationality of the composer */
    @Size(max = 255, message = "Nationality must not exceed 255 characters")
    @Column(name = "nationality")
    private String nationality;

    @Size(max = 255, message = "Place of death must not exceed 255 characters")
    @Column(name = "nameImage")
    private String nameImage;

    /**
     * Musical pieces written by the composer.
     * Bidirectional relationship (inverse side).
     * Inverse side of the OneToMany relationship.
     */
    @ToString.Exclude
    @OneToMany(mappedBy = "composer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MusicalPiece> musicalPieces = new HashSet<>();

    /**
     * Profile photo of the composer.
     * Bidirectional one-to-one relationship (inverse side).
     * Inverse side of the OneToOne relationship.
     */
    @ToString.Exclude
    @OneToOne(mappedBy = "composer")
    @JsonIgnore
    private PhotoComposer photo;
}

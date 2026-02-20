package com.example.favourite_composer.musicalpiece.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.favourite_composer.musicalpiece.domain.MusicalPiece;

/**
 * Repository for MusicalPiece entity.
 */
// @RepositoryRestResource(path = "piece", collectionResourceRel = "piece")
public interface MusicalPieceRepository extends JpaRepository<MusicalPiece, Long> {
    /**
     * Counts how many musical pieces are associated with a given composer.
     * Used to prevent deleting a composer with assigned musical pieces.
     *
     * @param composerId id of the composer
     * @return number of musical pieces
     */
    @Query("select count(m) from MusicalPiece m where m.composer.id = ?1")
    long countByComposerId(Long composerId);

    /**
     * Finds musical pieces whose title contains the given text (case-insensitive).
     *
     * @param title text to search in the title
     * @return list of matching musical pieces
     */
    List<MusicalPiece> findByTitleContainingIgnoreCase(String title);
}
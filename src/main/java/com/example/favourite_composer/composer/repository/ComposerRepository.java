package com.example.favourite_composer.composer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.favourite_composer.composer.domain.Composer;

/**
 * Repository for Composer entity.
 * Provides basic CRUD operations.
 */
// @RepositoryRestResource(path = "composer", collectionResourceRel =
// "composers")
public interface ComposerRepository extends JpaRepository<Composer, Long> {

    /**
     * Finds composers whose name contains the given text (case-insensitive).
     */
    List<Composer> findByNameContainingIgnoreCase(String name);

}
package com.example.favourite_composer.photocomposer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.favourite_composer.photocomposer.domain.PhotoComposer;

/**
 * Repository for PhotoComposer entity.
 */
public interface PhotoComposerRepository extends JpaRepository<PhotoComposer, Long> {
}

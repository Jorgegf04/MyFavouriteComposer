package com.example.favourite_composer.exceptions;

public class MusicalPieceNotFoundException extends RuntimeException {
    public MusicalPieceNotFoundException(Long id) {
        super("Musical piece with ID " + id + "was not found");
    }
}

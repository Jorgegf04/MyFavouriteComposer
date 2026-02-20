package com.example.favourite_composer.exceptions;

public class ComposerNotFoundException extends RuntimeException {
    public ComposerNotFoundException(Long id) {
        super("Cannot find composer with ID: " + id);
    }
}

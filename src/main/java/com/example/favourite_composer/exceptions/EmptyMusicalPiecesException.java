package com.example.favourite_composer.exceptions;

public class EmptyMusicalPiecesException extends RuntimeException {
    public EmptyMusicalPiecesException() {
        super("There are no musical pieces available");
    }
}

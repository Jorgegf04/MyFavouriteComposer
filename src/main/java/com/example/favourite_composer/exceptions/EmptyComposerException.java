package com.example.favourite_composer.exceptions;

public class EmptyComposerException extends RuntimeException {
    public EmptyComposerException() {
        super("There are no composers in the system");
    }
}

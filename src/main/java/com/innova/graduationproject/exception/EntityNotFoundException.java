package com.innova.graduationproject.exception;

public class EntityNotFoundException  extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EntityNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
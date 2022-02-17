package com.innova.graduationproject.exception;

public class CustomerIsAlreadyExistException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CustomerIsAlreadyExistException(String errorMessage) {
        super(errorMessage);
    }
}
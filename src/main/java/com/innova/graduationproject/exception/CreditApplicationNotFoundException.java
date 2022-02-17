package com.innova.graduationproject.exception;

public class CreditApplicationNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CreditApplicationNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
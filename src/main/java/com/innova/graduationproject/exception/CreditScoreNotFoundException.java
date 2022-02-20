package com.innova.graduationproject.exception;

public class CreditScoreNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CreditScoreNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
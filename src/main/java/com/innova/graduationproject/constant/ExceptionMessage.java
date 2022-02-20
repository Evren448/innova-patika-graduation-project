package com.innova.graduationproject.constant;

public enum ExceptionMessage {
    CUSTOMER_NOT_FOUND("Customer Not Found!"),
    CUSTOMER_IS_ALREADY_EXIST("Customer Is Already Exist!"),
    CREDIT_SCORE_NOT_FOUND("Credit Score Not Found!"),
    CREDIT_APPLICATION_NOT_FOUND("Credit Application Not Found!");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

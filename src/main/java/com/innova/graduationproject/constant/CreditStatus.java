package com.innova.graduationproject.constant;

public enum CreditStatus {
    ACCEPTED("ACCEPTED", "Onay"),
    REJECTED("REJECTED", "Red");

    private final String status;
    private final String message;

    CreditStatus(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}

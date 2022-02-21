package com.innova.graduationproject.constant;

public enum CreditStatus {
    ACCEPTED("ACCEPTED"),
    REJECTED("REJECTED");

    private final String status;

    CreditStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}

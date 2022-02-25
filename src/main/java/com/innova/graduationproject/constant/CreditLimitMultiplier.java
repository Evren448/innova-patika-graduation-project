package com.innova.graduationproject.constant;

public enum CreditLimitMultiplier {

    MULTIPLIER_BY(4),
    CREDIT_LIMIT_SCORE_LINE500(500),
    CREDIT_LIMIT_SCORE_LINE1000(1000);

    private Integer value;

    CreditLimitMultiplier(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}

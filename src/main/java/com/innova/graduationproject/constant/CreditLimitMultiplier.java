package com.innova.graduationproject.constant;

public enum CreditLimitMultiplier {

    MULTIPLIER_BY(4);

    private Integer multiplierValue;

    CreditLimitMultiplier(Integer multiplierValue) {
        this.multiplierValue = multiplierValue;
    }

    public Integer getMultiplierValue() {
        return multiplierValue;
    }
}

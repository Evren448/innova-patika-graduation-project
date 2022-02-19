package com.innova.graduationproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Jacksonized
public class CreditDto {

    private Long id;
    private BigDecimal creditValue;
    private String creditStatus;
    private String customerIdentityNumber;
    private BigDecimal customerIncome;


}

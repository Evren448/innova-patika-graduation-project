package com.innova.graduationproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRestDto {
    private String identityNumber;

    private String fullName;

    private String phoneNumber;

    private BigDecimal income;

    private Integer creditScore;
}

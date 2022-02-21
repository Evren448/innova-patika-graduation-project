package com.innova.graduationproject.dto.creditapplication;

import com.innova.graduationproject.constant.CreditStatus;
import com.innova.graduationproject.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditApplicationResponseDto {
    private Long id;

    private BigDecimal creditValue;

    private CreditStatus creditStatus;

    private Customer customer;

    private BigDecimal salary;

    private Integer creditScore;
}

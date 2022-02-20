package com.innova.graduationproject.dto.creditscore;

import com.innova.graduationproject.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditScoreRequestDto {
    private Long id;

    private Integer score;

    private Customer customer;
}

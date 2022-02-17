package com.innova.graduationproject.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponseDto {

    private Long id;

    private String identityNumber;

    private String fullName;

    private String phoneNumber;

    private BigDecimal income;

}

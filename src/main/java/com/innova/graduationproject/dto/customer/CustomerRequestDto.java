package com.innova.graduationproject.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRequestDto {

    private Long id;

    @NotNull(message="Identity Number should not be null.")
    @NotBlank(message="Identity Number should not blank.")
    @Size(min = 11, max = 11, message = "Identity Number size has to be 11")
    @Pattern(regexp = "^\\d+$", message = "Identity Number can include only numbers.")
    private String identityNumber;

    @NotNull(message="Full name cannot be null.")
    @NotBlank(message="Full name cannot be blank.")
    private String fullName;

    @NotNull(message="Phone Number cannot be null.")
    @NotBlank(message="Phone Number cannot be null.")
    @Size(min = 10, max = 10, message = "Phone number size has to be 10")
    @Pattern(regexp = "^\\d+$", message = "Phone Number can include only numbers.")
    private String phoneNumber;

    @NotNull(message = "Income should not be null")
    @PositiveOrZero(message = "Income should be positive or zero")
    private BigDecimal income;


}

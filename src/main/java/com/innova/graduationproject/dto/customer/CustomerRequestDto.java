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

    //TODO mesajlar bakilacak.
    @NotNull(message="{identity.not-null.message}")
    @NotBlank(message="{identity.not-blank.message}")
    @Size(min = 11, max = 11, message = "{creditsystem.constraints.identity.Size.message}")
    @Pattern(regexp = "^\\d+$", message = "{identity.pattern.message}")
    private String identityNumber;

    @NotNull
    @NotBlank
    private String fullName;

    @NotNull
    @NotBlank
    @Size(min = 10, max = 10, message = "Phone number 10 olmali hatasi")
    @Pattern(regexp = "^\\d+$", message = "TC identification no must include only numbers.")
    private String phoneNumber;

    @NotNull(message = "Income should not be null")
    @PositiveOrZero(message = "Income should be positive or zero")
    private BigDecimal income;


}

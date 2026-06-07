package com.inv_managemnt.inv_backend.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor@NoArgsConstructor
public class UpdateProductDTO {
    @NotBlank
    @Pattern(
            regexp = "^[A-Z0-9_-]+$",
            message = "Invalid format(Only uppercase, digits and '-', '_' is allowed)"
    )
    private String sku;

    @NotBlank(message = "name is required")
    private String name;

    @NotNull
    @Positive
    @Digits(integer = 10, fraction = 2)
    private BigDecimal price;
}

package com.inv_managemnt.inv_backend.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvDTO {

    @NotNull
    @Positive
    private Integer availableQuantity = 0;

    @NotNull
    @Positive
    private Integer reservedQuantity = 0;
}

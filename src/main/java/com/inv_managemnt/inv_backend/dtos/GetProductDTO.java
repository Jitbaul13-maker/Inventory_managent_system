package com.inv_managemnt.inv_backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetProductDTO implements Serializable {
    private String sku;

    private String name;

    private BigDecimal price;
}

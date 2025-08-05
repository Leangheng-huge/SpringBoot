package com.chiikawa.demo.DTO.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    @NotBlank(message = "Product must not be blank")
    private String name;

    private String description;

    @NotNull(message = "Price is require")
    @Positive(message = "Price must be positive cannot be negative")
    private Double price;
}

package com.chiikawa.demo.DTO.Stock;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockDto {

    @NotNull(message = "stock is is require")
    @Positive(message = "stock cannot be negative ")
    private Long productId;

    @Min(value = 0, message = "quantity must not be zero or more" )
    private Integer quantity;
}

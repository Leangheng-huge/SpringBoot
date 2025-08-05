package com.chiikawa.demo.DTO.Stock;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateStockDto {
    @NotNull(message = "operation type is require")
    @Min(value = 1, message = "operation must be in [1,2]")
    @Max(value = 2, message = "operation type must be in [1,2]")
    private Integer operationType ;

    @NotNull(message = "quantity is required")
    @Min(value = 1 , message = "quantity must be at least 1")
    private Integer quantity;
}

package com.chiikawa.demo.DTO.Stock;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StockResponseDto {

    @JsonProperty("Stock_id")
    private Long id;

    @JsonProperty("product_id")
    private Long ProductId;

    @JsonProperty("quantity")
    private Integer qty;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}

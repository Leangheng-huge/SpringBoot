package com.chiikawa.demo.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductResponseDto {

    @JsonProperty("product_id")
    private Long id;

    @JsonProperty("price")
    private Double price;

    private String description;
}

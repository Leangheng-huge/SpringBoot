package com.chiikawa.demo.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class OrderDto {
    @JsonProperty("items")
    @NotNull(message = "order itemm is require")
    @NotEmpty(message = "order item cannot be empty")
    private List<OrderItemDto> orderItems;
}

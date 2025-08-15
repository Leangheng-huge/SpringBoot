package com.chiikawa.demo.DTO.order;


import com.chiikawa.demo.common.annotations.ValidEnum;
import com.chiikawa.demo.common.enums.OrderStatus;
import com.chiikawa.demo.service.OrderService;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Data
@RestController
@RequestMapping("/api/v1/order")
public class OrderUpdateDto {

    @Autowired
    private OrderService orderService;

    @JsonProperty("order")
    @ValidEnum(enumClass = OrderStatus.class, message = "Value must be one of PENDING, FAILED, SUCCESS")
    public String status;

}

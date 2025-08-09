package com.chiikawa.demo.Mapper;


import com.chiikawa.demo.DTO.OrderItemDto;
import com.chiikawa.demo.entity.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {
    public OrderItem toEntity(OrderItemDto dto){
        OrderItem entity = new OrderItem();

        entity.setProductId(dto.getProductId());
        entity.setQuantity(dto.getAmount());

        return entity;
    }
}

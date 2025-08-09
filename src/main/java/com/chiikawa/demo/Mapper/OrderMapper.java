package com.chiikawa.demo.Mapper;

import com.chiikawa.demo.DTO.OrderDto;
import com.chiikawa.demo.DTO.OrderItemDto;
import com.chiikawa.demo.entity.Order;
import com.chiikawa.demo.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {
    @Autowired
    private OrderItemMapper orderItemMapper;

    public Order toEntity(OrderDto dto) {
        Order entity = new Order();

        // order item entities
        List<OrderItem> orderItemEntities = dto.getOrderItems()
                .stream()
                .map(orderItemDto -> {
                    OrderItem orderItem = orderItemMapper.toEntity(orderItemDto);
                    orderItem.setOrder(entity);

                    return orderItem;
                })
                .toList();

        entity.setItems(orderItemEntities);

        return entity;
    }

}
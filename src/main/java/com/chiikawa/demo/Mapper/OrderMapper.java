package com.chiikawa.demo.Mapper;

import com.chiikawa.demo.DTO.order.OrderDto;
import com.chiikawa.demo.DTO.order.OrderItemResponseDto;
import com.chiikawa.demo.DTO.order.OrderResponseDto;
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

    public OrderResponseDto toResponseDto(Order entity) {
        if(entity == null) {
            return null;
        }

        OrderResponseDto dto = new OrderResponseDto();
        dto.setId(entity.getId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setStatus(entity.getStatus());

        if(entity.getItems() != null && !entity.getItems().isEmpty()) {
            List<OrderItemResponseDto> orderItemDtos = orderItemMapper.toResponseDtoList(entity.getItems());

            dto.setItems(orderItemDtos);

            // map for total price
            Double total = orderItemDtos.stream()
                    .mapToDouble(orderItemResponseDto -> {
                        return orderItemResponseDto.getPurchaseAmount() * orderItemResponseDto.getUnitPrice();
                    })
                    .sum();

            dto.setTotal(total);
        }

        return dto;
    }

    public List<OrderResponseDto> toResponseDtoList(List<Order> entities) {
        return entities.stream()
                .map(order -> this.toResponseDto(order))
                .toList();
    }

}
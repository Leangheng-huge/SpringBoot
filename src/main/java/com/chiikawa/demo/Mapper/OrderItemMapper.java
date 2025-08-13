package com.chiikawa.demo.Mapper;


import com.chiikawa.demo.DTO.order.OrderItemDto;
import com.chiikawa.demo.DTO.order.OrderItemResponseDto;
import com.chiikawa.demo.entity.OrderItem;
import com.chiikawa.demo.entity.Product;
import com.chiikawa.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.module.ResolutionException;
import java.util.List;

@Component
public class OrderItemMapper {

    @Autowired
    private ProductRepository productRepository;

    public OrderItem toEntity(OrderItemDto dto){
        OrderItem entity = new OrderItem();

        entity.setProductId(dto.getProductId());
        entity.setQuantity(dto.getAmount());

        return entity;
    }

    public OrderItemResponseDto toDto(OrderItem entity){

        if(entity == null) {
            return null;
        }
        OrderItemResponseDto dto = new OrderItemResponseDto();
        dto.setProductId(entity.getProductId());
        dto.setPurchaseAmount(entity.getQuantity());

        // query get product
        Product product = productRepository.findById(entity.getProductId())
                .orElseThrow(() -> new ResolutionException("product not found with id: " + entity.getProductId()));

        dto.setProductName(product.getProductName());
        dto.setUnitPrice(product.getPrice());

        return dto;
    }

    public List<OrderItemResponseDto> toResponseDtoList(List<OrderItem> entities){
        return entities.stream()
                .map(entity -> this.toDto(entity))
                .toList();
    }
}

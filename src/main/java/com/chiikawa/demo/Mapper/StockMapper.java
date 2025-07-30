package com.chiikawa.demo.Mapper;


import com.chiikawa.demo.DTO.Stock.StockDto;
import com.chiikawa.demo.DTO.Stock.StockResponseDto;
import com.chiikawa.demo.entity.Product;
import com.chiikawa.demo.entity.Stock;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StockMapper {
    public Stock toEntity(StockDto dto, Product product){
        Stock entity = new Stock();

        entity.setQuantity(dto.getQuantity());
        entity.setProduct(product);

        return entity;
    }

    public StockResponseDto toDto(Stock entity){
        StockResponseDto dto = new StockResponseDto();

        dto.setId(entity.getId());
        dto.setProductId(entity.getProduct().getId());
        dto.setQty(entity.getQuantity());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        return dto;
    }

    public List<StockResponseDto> toDoList(List<Stock> entites){
        if ((entites == null || entites.isEmpty())){
            return new ArrayList<>();
        }

        return entites.stream()
                .map(stock -> this.toDto(stock))
                .collect(Collectors.toList());
    }
}

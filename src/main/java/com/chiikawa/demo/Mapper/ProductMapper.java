package com.chiikawa.demo.Mapper;

import com.chiikawa.demo.DTO.Product.ProductDto;
import com.chiikawa.demo.DTO.Product.ProductResponseDto;
import com.chiikawa.demo.entity.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {
    public Product toEntity(ProductDto dto) {
        Product entity = new Product();

        entity.setProductName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());

        return entity;
    }

    public ProductResponseDto toDto(Product entity) {
        ProductResponseDto dto = new ProductResponseDto();

        dto.setProductId(entity.getId());
        dto.setProductName(entity.getProductName());
        dto.setPrice(entity.getPrice());
        dto.setDescription(entity.getDescription());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        return dto;
    }

    public List<ProductResponseDto> toDtoList(List<Product> entities) {
        if (entities == null || entities.isEmpty()) {
            return new ArrayList<>();
        }

        return entities.stream()
                .map(product -> this.toDto(product))
                .collect(Collectors.toList());
    }
}

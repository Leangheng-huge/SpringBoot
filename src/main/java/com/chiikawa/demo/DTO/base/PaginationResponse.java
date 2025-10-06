package com.chiikawa.demo.DTO.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;


import java.util.List;


@Data
@JsonPropertyOrder({"content","pagination"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class PaginationResponse<T> {
    private List<T> content;
    private Object pagination;

    public static <T> PaginationResponse<T> from(Page<T> page) {
        return new PaginationResponse<>(page.getContent(), page.getPageable()); 
    }
}

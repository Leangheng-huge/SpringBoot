package com.chiikawa.demo.DTO.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;

@JsonPropertyOrder(value = {"page","size","total_elements","total_pages","first","last","has_next","has_previous","links"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
public class PaginationMetadata {
    private int page;
    private int size;

    @JsonProperty("total_elements")
    private long totalElements;

    @JsonProperty("total_pages")
    private int totalPages;

    private boolean first;
    private boolean last;

    @JsonProperty("has_next")
    private boolean hasNext;

    @JsonProperty("has_previous")
    private boolean hasPrevious;
    private Link links;
}

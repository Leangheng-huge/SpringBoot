package com.chiikawa.demo.DTO.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonPropertyOrder(value = {"self","first","last","previous","next"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Link {
    private String self;
    private String next;
    private String previous;
    private String first;
    private String last;
}

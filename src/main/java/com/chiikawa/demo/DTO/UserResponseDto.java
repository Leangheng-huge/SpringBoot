package com.chiikawa.demo.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserResponseDto {
    @JsonProperty
    private Long id;

    @JsonProperty
    private String name;

    private Integer age;

    @JsonProperty("location")
    private String address;
    private String email;
    private String role = "USER";
}

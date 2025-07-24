package com.chiikawa.demo.DTO.User;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.time.LocalDateTime;

@JsonPropertyOrder({"user_id","username","email","age","location","role","created_at","updated_at"})
@Data
public class UserResponseDto {
    @JsonProperty("user_id")
    private Long id;

    @JsonProperty("username")
    private String name;

    private Integer age;

    @JsonProperty("location")
    private String address;
    private String email;
    private String role = "USER";

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}

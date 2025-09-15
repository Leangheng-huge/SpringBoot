package com.chiikawa.demo.DTO.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RefreshTokenDto {
    @JsonProperty("refresh_token")
    private String refreshToken;
}

package com.chiikawa.demo.DTO.supplier;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SupplierDto {

    @NotBlank(message = "supplier name is require")
    private String name;

    private String address;
    private String rating;
    private String phone;

    @Email(message = "email should be valid")
    private String email;

}

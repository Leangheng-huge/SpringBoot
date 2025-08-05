package com.chiikawa.demo.DTO.supplier;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SupplierDto {

    @NotNull(message = "supplier name is require")
    @NotBlank(message = "supplier name must not be blank")
    @Size(min = 5, max = 20 , message = "supplier name must be between 5 to 20 characters")
    private String name;

    private String address;
    private String rating;
    private String phone;

    @Email(message = "email should be valid")
    private String email;

}

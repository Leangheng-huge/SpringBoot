package com.chiikawa.demo.DTO.supplier;


import lombok.Data;

@Data
public class UpdateSupplierDto {
    private String name;
    private String address;
    private String rating;
    private String phone;
    private String email;
    
}

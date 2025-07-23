package com.chiikawa.demo.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private Integer age;
    private String address;
    private String password;
    private String email;
    private String role= "USER";

}

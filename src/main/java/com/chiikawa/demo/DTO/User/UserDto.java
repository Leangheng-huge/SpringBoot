package com.chiikawa.demo.DTO.User;

import com.chiikawa.demo.common.annotations.ValidEnum;
import com.chiikawa.demo.common.enums.Role;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @NotNull(message = "username is required")
    @Size(min = 4, max = 50, message = "username must be between 4 and 50 characters")
    private String name;

    @NotNull(message = "password is required")
    @Size(min = 8, max = 20, message = "password must be between 8 and 20 characters")
    private String password;

    @NotNull(message = "age is required")
    @Min(value = 18, message = "age must be at least 18")
    private Integer age;

    @NotNull(message = "address is required")
    @Size(min = 5, max = 50, message = "address must be between 5 and 50 characters")
    private String address;

    @NotNull(message = "email is required")
    @Email(message = "email must be valid")
    private String email;

    @ValidEnum(enumClass = Role.class, message = "Role must be in [USER,ADMIN]")
    private String role = "USER";
}
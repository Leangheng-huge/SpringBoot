package com.chiikawa.demo.DTO.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NotNull(message = "id is require")
    private Long id;

    @NotBlank(message = "name cannot be blank")
    private String name;
    private Integer age;
    private String address;

    @NotBlank(message = "password must be provided")
    @Size(min = 6, message = "password must be at least 6 characters")
    private String password;

    @NotBlank(message = "email is needed")
    @Email(message = "email should be valid")
    private String email;
    private String role= "USER";

}

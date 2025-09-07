package com.chiikawa.demo.Controller;

import com.chiikawa.demo.DTO.User.ChangePasswordUserDto;
import com.chiikawa.demo.DTO.User.UpdateUserDto;
import com.chiikawa.demo.DTO.User.UserResponseDto;
import com.chiikawa.demo.DTO.base.Response;

import com.chiikawa.demo.DTO.User.UserDto;

import com.chiikawa.demo.service.UserService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    // used for retrieving records
    @GetMapping
    public ResponseEntity<Response> listUsers() {
        List<UserResponseDto> users = userService.listUsers();

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("200","success","successfully retrieve users",users));
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<Response> getUser(@PathVariable("user_id") Long userId) {
        UserResponseDto user = userService.getUser(userId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("200","success","user found",user));
    }

    // used for creating/inserting record
    // request body can be called request payload or shortcut "payload"


    //  endpoint -> /api/v1/users/1551316
    @PutMapping("/{user_id}")
    public ResponseEntity<Response> updateUser(@PathVariable("user_id") Long userId,@Valid @RequestBody UpdateUserDto payload) {
        userService.updateUser(payload,userId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("success","successfully updated user"));
    }

    // Path variable
    @DeleteMapping("/{user_id}")
    public ResponseEntity<Response> deleteUser(@PathVariable("user_id") Long userId) {
        userService.deleteUser(userId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("success","successfully deleted user"));
    }

    // change password
    @PatchMapping("/{user_id}/change-password")
    public ResponseEntity<Response> changePassword(@PathVariable("user_id") Long userId,
                                                   @RequestBody ChangePasswordUserDto payload) {
        userService.changePassword(payload, userId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("success","successfully changed password"));
    }
}
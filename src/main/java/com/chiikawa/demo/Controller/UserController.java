package com.chiikawa.demo.Controller;

import com.chiikawa.demo.model.BaseResponseModel;
import com.chiikawa.demo.DTO.User.UserDto;
import com.chiikawa.demo.model.BaseResponseWithDataModel;
import com.chiikawa.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    // used for retrieving records
    @GetMapping
    public ResponseEntity<BaseResponseWithDataModel> listUsers() {
        return userService.listUsers();
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<BaseResponseWithDataModel> getUser(@PathVariable("user_id") Long userId) {
        return userService.getUser(userId);
    }

    // used for creating/inserting record
    // request body can be called request payload or shortcut "payload"
    @PostMapping
    public ResponseEntity<BaseResponseModel> createUser(@RequestBody UserDto payload) {
        return userService.createUser(payload);
    }

    //  endpoint -> /api/v1/users/923482348284
    @PutMapping("/{user_id}")
    public ResponseEntity<BaseResponseModel> updateUser(@PathVariable("user_id") Long userId, @RequestBody UserDto payload) {
        return userService.updateUser(payload,userId);
    }

    // Path variable
    @DeleteMapping("/{user_id}")
    public ResponseEntity<BaseResponseModel> deleteUser(@PathVariable("user_id") Long userId) {
        return userService.deleteUser(userId);
    }
}

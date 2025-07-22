package com.chiikawa.demo.Controller;

import com.chiikawa.demo.model.BaseResponseModel;
import com.chiikawa.demo.model.UserModel;
import com.chiikawa.demo.model.UserResponseModel;
import com.chiikawa.demo.model_product.BaseResponseWithDataModel;
import com.chiikawa.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
@Autowired
UserService userService;
    private List<UserModel> users = new ArrayList<>(Arrays.asList(
            new UserModel(1L, "John", 25, "USA","","")
    ));

    // used for retrieving records from the database
    @GetMapping
    public ResponseEntity<UserResponseModel> listUsers() {
      return userService.getUser();
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<BaseResponseWithDataModel> getUser(@PathVariable("user_id") Long userId){
        return userService.getUser(userId);
    }

    // used for creating / inserting records into the database
    // request body can be called request payload or shortcut "payload"
    @PostMapping
    public ResponseEntity<BaseResponseModel> createUser(@RequestBody UserModel payload) {
       return userService.createUser(users, payload);
    }

    @PutMapping("/{user_id}")
    public ResponseEntity<BaseResponseModel> updateUser(@PathVariable("user_id") Long userId, @RequestBody UserModel payload) {

       return userService.updateUser(users, payload, userId);
    }

    // Path variable
    @DeleteMapping("/{user_id}")
    public ResponseEntity<BaseResponseModel> deleteUser(@PathVariable("user_id") Long userId) {
        // not found, every array starts from 0
        return userService.deleteUser(users, userId);
    }
}

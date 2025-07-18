package com.chiikawa.demo.service;

import com.chiikawa.demo.entity.User;
import com.chiikawa.demo.model.BaseResponseModel;
import com.chiikawa.demo.model.UserModel;
import com.chiikawa.demo.model.UserResponseModel;
import com.chiikawa.demo.model_product.BaseResponseModelOfProduct;
import com.chiikawa.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<UserResponseModel> getUser(){

        List<User> userData = userRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new UserResponseModel("success", "Users retrieved", userData));
    }

    public ResponseEntity<BaseResponseModelOfProduct> getUser(Long id){
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponseModelOfProduct("Fail", "User not found with id: " + id,null));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModelOfProduct("success", "User retrieved", user.get()));
    }

    public ResponseEntity<BaseResponseModel> createUser(List<UserModel> users, UserModel payload){
        User user = new User();
        user.setName(payload.getName());
        user.setAge(payload.getAge());
        user.setAddress(payload.getAddress());
        user.setEmail(payload.getEmail());
        user.setCreatedAt(LocalDateTime.now());
        user.setRole(payload.getRole());

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new BaseResponseModel("success", "User created"));
    }

    public ResponseEntity<BaseResponseModel> updateUser(List<UserModel> users, UserModel payload, Long userId){

        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isEmpty()){
            // if user not found response with 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponseModel("Fail", "user not found with id: " + userId));
        }

        // user found, update it
        User updatedUser = existingUser.get();

        // modify values
        updatedUser.setName(payload.getName());
        updatedUser.setAge(payload.getAge());
        updatedUser.setEmail(payload.getEmail());
        updatedUser.setAddress(payload.getAddress());
        updatedUser.setRole(payload.getRole());
        userRepository.save(updatedUser);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("success", "User updated"));
    }

    public ResponseEntity<BaseResponseModel> deleteUser(List<UserModel> users, Long userId){
        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isEmpty()){
            // if user not found response with 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponseModel("Fail", "user not found with id: " + userId));
        }

        // user found, then delete it
        userRepository.deleteById(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("success", "User deleted"));
    }

}

package com.chiikawa.demo.service;

import com.chiikawa.demo.DTO.User.ChangePasswordUserDto;
import com.chiikawa.demo.DTO.User.UpdateUserDto;
import com.chiikawa.demo.DTO.User.UserResponseDto;
import com.chiikawa.demo.Exception.model.DuplicateResourceException;
import com.chiikawa.demo.Exception.model.ResourceNotFoundException;
import com.chiikawa.demo.Mapper.UserMapper;
import com.chiikawa.demo.entity.User;
import com.chiikawa.demo.model.BaseResponseModel;
import com.chiikawa.demo.DTO.User.UserDto;
import com.chiikawa.demo.model.BaseResponseWithDataModel;
import com.chiikawa.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper mapper;

    public ResponseEntity<BaseResponseWithDataModel> listUsers() {
        List<User> users = userRepository.findAll();

        List<UserResponseDto> dtos = mapper.toDtoList(users);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseWithDataModel("success","successfully retrieve users",dtos));
    }

    public ResponseEntity<BaseResponseWithDataModel> getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found with id : " + userId));

        UserResponseDto dto = mapper.toDto(user);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseWithDataModel("success","user found",dto));
    }

    public ResponseEntity<BaseResponseModel> createUser(UserDto payload) {
        // validate if username is existed
        if (userRepository.existsByName(payload.getName())){
            throw new DuplicateResourceException("Username is already existed.");
        }

        // validate if email is existed
        if (userRepository.existsByEmail(payload.getEmail())){
            throw new DuplicateResourceException("Email is already existed.");
        }

        User user = mapper.toEntity(payload);

        userRepository.save(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new BaseResponseModel("success","successfully created user"));
    }

    public ResponseEntity<BaseResponseModel> updateUser(UpdateUserDto payload, Long userId) {
        User existing = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found with id: " + userId));

        // modify values
        mapper.updateEntityFromDto(existing,payload);

        userRepository.save(existing);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("success","successfully updated user"));
    }

    public ResponseEntity<BaseResponseModel> deleteUser(Long userId) {
        // if user not found, then response 404
        if(!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("user not found with id: " + userId);
        }

        // user found , then delete
        userRepository.deleteById(userId);

        // 200 OK
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("success","successfully deleted user"));
    }

    public ResponseEntity<BaseResponseModel> changePassword(ChangePasswordUserDto payload, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found with id: " + userId));

        // old password is incorrect
        if(!Objects.equals(user.getPassword(), payload.getOldPassword())) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new BaseResponseModel("fail","old password is incorrect, please enter the correct password"));
        }

        // new password and confirm password not match
        if(!Objects.equals(payload.getNewPassword(), payload.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new BaseResponseModel("fail","new password and confirm password must be the same"));
        }

        mapper.updateEntityChangePassword(user, payload.getNewPassword());
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("success","successfully changed password"));
    }
}
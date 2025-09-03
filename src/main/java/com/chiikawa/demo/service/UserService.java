package com.chiikawa.demo.service;

import com.chiikawa.demo.DTO.User.ChangePasswordUserDto;
import com.chiikawa.demo.DTO.User.UpdateUserDto;
import com.chiikawa.demo.DTO.User.UserResponseDto;
import com.chiikawa.demo.Exception.model.DuplicateResourceException;
import com.chiikawa.demo.Exception.model.ResourceNotFoundException;
import com.chiikawa.demo.Exception.model.UnprocessableEntityException;
import com.chiikawa.demo.Mapper.UserMapper;
import com.chiikawa.demo.entity.User;

import com.chiikawa.demo.DTO.User.UserDto;

import com.chiikawa.demo.repository.UserRepository;
import com.chiikawa.demo.service.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private JwtUtil jwtUtil;

    public List<UserResponseDto> listUsers() {
        List<User> users = userRepository.findAll();

        return mapper.toDtoList(users);
    }

    public UserResponseDto getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found with id : " + userId));

        String token = jwtUtil.generateToken(user);

        System.out.println("Token: " + token );

        return mapper.toDto(user);
    }

    public void createUser(UserDto payload) {
        // validate if username is existed
        if(userRepository.existsByName(payload.getName())) {
            throw new DuplicateResourceException("username is already existed");
        }

        // validate if email is existed
        if(userRepository.existsByEmail(payload.getEmail())) {
            throw new DuplicateResourceException("email is already existed");
        }

        User user = mapper.toEntity(payload);

        userRepository.save(user);
    }

    public void updateUser(UpdateUserDto payload, Long userId) {
        User existing = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found with id: " + userId));

        // modify values
        mapper.updateEntityFromDto(existing,payload);

        userRepository.save(existing);
    }

    public void deleteUser(Long userId) {
        // if user not found, then response 404
        if(!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("user not found with id: " + userId);
        }

        // user found , then delete
        userRepository.deleteById(userId);
    }

    public void changePassword(ChangePasswordUserDto payload, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found with id: " + userId));

        // old password is incorrect
        if(!Objects.equals(user.getPassword(), payload.getOldPassword())) {
            throw new UnprocessableEntityException("old password is incorrect, please enter the correct password");
        }

        // new password and confirm password not match
        if(!Objects.equals(payload.getNewPassword(), payload.getConfirmPassword())) {
            throw new UnprocessableEntityException("new password and confirm password must be the same");
        }

        mapper.updateEntityChangePassword(user, payload.getNewPassword());
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws ResourceNotFoundException {
        return userRepository.findByName(username)
                .orElseThrow(() ->{throw new ResourceNotFoundException("user not found with username: " + username);}
                );
    }
}
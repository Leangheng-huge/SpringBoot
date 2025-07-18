package com.chiikawa.demo.model;

import com.chiikawa.demo.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class UserResponseModel extends BaseResponseModel {
    private List<User> users;
    public UserResponseModel(String status, String message, List<User> users) {
        super(status, message);
        this.users = users;
    }
}

package com.akgul.starbux.service;

import com.akgul.starbux.controller.UserController;
import com.akgul.starbux.entity.User;
import com.akgul.starbux.enums.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserController userController;

    public User getUserById(Long userId) {
        return userController.getUserById(userId);
    }

    public User getUserByUserName(String userName) {
        return userController.getUserByUserName(userName);
    }

    public List<User> getUsers() {
        return userController.getUsers();
    }

    public List<User> getAdminUsers() {
        return userController.getUsersByUserType(UserType.ADMIN);
    }

    public List<User> getCustomerUsers() {
        return userController.getUsersByUserType(UserType.CUSTOMER);
    }
}

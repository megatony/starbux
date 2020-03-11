package com.akgul.starbux.service;

import com.akgul.starbux.controller.UserController;
import com.akgul.starbux.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserController userController;

    public User getUserByUserName(String userName) {
        return userController.getUserByUserName(userName);
    }

}

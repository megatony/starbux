package com.akgul.starbux.controller;

import com.akgul.starbux.entity.User;
import com.akgul.starbux.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    @Autowired
    private UserRepository repository;

    public User getUserByUserName(String userName) {
        return repository.getByUserNameAndDeleted(userName, false);
    }
}

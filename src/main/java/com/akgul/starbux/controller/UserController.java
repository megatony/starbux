package com.akgul.starbux.controller;

import com.akgul.starbux.entity.User;
import com.akgul.starbux.enums.UserType;
import com.akgul.starbux.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserRepository repository;

    public User getUserById(Long userId) {
        return repository.getByIdAndDeleted(userId, false);
    }

    public User getUserByUserName(String userName) {
        return repository.getByUserNameAndDeleted(userName, false);
    }

    public List<User> getUsers() {
        return repository.findAllByDeleted(false);
    }

    public List<User> getUsersByUserType(UserType userType) {
        return repository.findAllByDeletedAndUserTypeEquals(false, userType);
    }
}

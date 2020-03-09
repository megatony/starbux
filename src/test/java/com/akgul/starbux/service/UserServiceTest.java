package com.akgul.starbux.service;

import com.akgul.starbux.controller.UserController;
import com.akgul.starbux.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserController userController;

    @Test
    public void shouldGetUserByUserName() {
        User user = new User();
        when(userController.getUserByUserName("user")).thenReturn(user);

        Assert.assertEquals(user, userService.getUserByUserName("user"));
    }
}

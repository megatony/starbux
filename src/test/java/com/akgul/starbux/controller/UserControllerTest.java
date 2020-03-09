package com.akgul.starbux.controller;

import com.akgul.starbux.entity.User;
import com.akgul.starbux.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserRepository userRepository;

    @Test
    public void shouldGetUserByUserName() {
        User user = new User();
        user.setUserName("test");
        user.setDeleted(true);

        User newUser = new User();
        newUser.setUserName("test");

        when(userRepository.getByUserNameAndDeleted("test", false)).thenReturn(newUser);

        Assert.assertEquals(userController.getUserByUserName("test"), newUser);
    }
}

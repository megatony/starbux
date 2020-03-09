package com.akgul.starbux.entity;

import com.akgul.starbux.enums.UserType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "USERS")
public class User extends StarbuxObject {

    @Column(name = "USER_NAME", unique = true)
    private String userName;

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_TYPE")
    private UserType userType = UserType.CUSTOMER;

}

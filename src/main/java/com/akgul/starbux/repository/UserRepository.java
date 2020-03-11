package com.akgul.starbux.repository;

import com.akgul.starbux.entity.User;
import com.akgul.starbux.enums.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getByUserNameAndDeleted(String userName, boolean deleted);
}

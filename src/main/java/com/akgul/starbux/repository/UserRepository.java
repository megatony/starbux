package com.akgul.starbux.repository;

import com.akgul.starbux.entity.User;
import com.akgul.starbux.enums.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getByIdAndDeleted(Long id, boolean deleted);
    User getByUserNameAndDeleted(String userName, boolean deleted);
    List<User> findAllByDeleted(boolean deleted);
    List<User> findAllByDeletedAndUserTypeEquals(boolean deleted, UserType userType);
}

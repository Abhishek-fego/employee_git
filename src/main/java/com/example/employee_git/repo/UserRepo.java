package com.example.employee_git.repo;

import com.example.employee_git.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepo extends JpaRepository<User,Long> {
    public User findByNumber(String number);

    @Query(
            value = "SELECT * FROM USERS u WHERE u.status = 0",
            nativeQuery = true)
    List<User> findAllActiveUsers();
}
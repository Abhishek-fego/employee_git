package com.example.employee_git.repo;

import com.example.employee_git.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRoleRepo extends JpaRepository<UserRole,Long> {
    @Query(
            value = "SELECT * FROM USER_ROLES u WHERE u.user_id = ?1",
            nativeQuery = true)
    UserRole findUserRoleByUserId(long user_id);

}

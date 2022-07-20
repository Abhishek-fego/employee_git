package com.example.employee_git.repo;

import com.example.employee_git.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Long> {
    public Role findByRole(String role);
}

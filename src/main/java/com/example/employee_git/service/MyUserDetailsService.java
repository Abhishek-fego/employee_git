package com.example.employee_git.service;

import com.example.employee_git.model.User;
import com.example.employee_git.repo.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    public MyUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User repoUser = userRepo.findByNumber(username);
        org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(repoUser.getNumber(), repoUser.getPassword(), new ArrayList<>());
        return user;
    }
}
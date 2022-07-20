package com.example.employee_git.controller;


import com.example.employee_git.model.AuthRequest;
import com.example.employee_git.model.AuthResponse;
import com.example.employee_git.model.RegisterRequest;
import com.example.employee_git.model.User;
import com.example.employee_git.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/register")
    public String registerUser(@RequestBody RegisterRequest registerRequest) throws Exception {
        return userService.registerUser(registerRequest);
    }

    @PreAuthorize("hasAnyRole('ROLE_full_access_user','ROLE_limited_access_user')")
    @GetMapping("/user")
    public List<User> getUsers(){
        return userService.getUsers();
    }


    @PreAuthorize("hasRole('ROLE_full_access_user')")
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable long id) throws Exception {
        return userService.deleteUser(id);
    }


    @PostMapping("/login")
    public AuthResponse checkLogin(@RequestBody AuthRequest authRequest) throws Exception {
        AuthResponse authResponse = new AuthResponse(userService.loginUser(authRequest));
        return authResponse;
    }


    @PreAuthorize("hasRole('ROLE_full_access_user')")
    @PutMapping("/update")
    public String updateUser(@RequestBody User user,@PathVariable long id){
        return userService.updateUser(user,id);
    }
}
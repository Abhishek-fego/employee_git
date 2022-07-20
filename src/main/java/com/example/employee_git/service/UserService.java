package com.example.employee_git.service;


import com.example.employee_git.model.AuthRequest;
import com.example.employee_git.model.RegisterRequest;
import com.example.employee_git.model.User;
import com.example.employee_git.model.UserRole;
import com.example.employee_git.repo.RoleRepo;
import com.example.employee_git.repo.UserRepo;
import com.example.employee_git.repo.UserRoleRepo;
import com.example.employee_git.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepo userRepo;

    private final RoleRepo roleRepo;

    private final UserRoleRepo userRoleRepo;
    private final AuthenticationManager authenticationManager;

    private final MyUserDetailsService userDetailsService;

    private final JwtUtil jwtUtil;


    public UserService(UserRepo userRepo, RoleRepo roleRepo, UserRoleRepo userRoleRepo, AuthenticationManager authenticationManager, MyUserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.userRoleRepo = userRoleRepo;

        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    public String registerUser(RegisterRequest registerRequest) throws Exception {

        try {
            User user = new User();
            user.setNumber(registerRequest.getNumber());
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(registerRequest.getPassword()));
            userRepo.save(user);
            long role_id = roleRepo.findByRole(registerRequest.getRole()).getId();
            long user_id = user.getId();

            UserRole user_role = new UserRole();
            user_role.setUser_id(user_id);
            user_role.setRole_id(role_id);

            userRoleRepo.save(user_role);
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }

        return "User registered successfully";


    }

    public List<User> getUsers(){
        return userRepo.findAllActiveUsers();
    }

    public String deleteUser(long id) throws Exception {
        User user = userRepo.findById(id).orElseThrow(()-> new Exception("No User to delete with the given id"));
        user.setStatus(1);
        userRepo.save(user);
        return "User deleted successfully!!";
    }

    public String loginUser(AuthRequest authRequest) throws Exception {
        User user = userRepo.findByNumber(authRequest.getNumber());
        if (user == null){
            throw new Exception("User not found");
        }
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getNumber(),authRequest.getPassword()));

        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getNumber());
        return jwtUtil.generateToken(userDetails);
    }

    public String updateUser(User user,long id){
        if(userRepo.findById(id).isPresent()){
            userRepo.save(user);
            return "updated successfully!!";
        }
        else{
            return "No user with the given id";
        }
    }

}
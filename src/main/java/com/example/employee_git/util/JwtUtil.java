package com.example.employee_git.util;


import com.example.employee_git.model.Role;
import com.example.employee_git.model.User;
import com.example.employee_git.model.UserRole;
import com.example.employee_git.repo.RoleRepo;
import com.example.employee_git.repo.UserRepo;
import com.example.employee_git.repo.UserRoleRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

@Service
public class JwtUtil {


    private final UserRepo userRepo;

    private final UserRoleRepo userRoleRepo;

    private final RoleRepo roleRepo;


    public JwtUtil(UserRepo userRepo, UserRoleRepo userRoleRepo, RoleRepo roleRepo) {

        this.userRepo = userRepo;
        this.userRoleRepo = userRoleRepo;
        this.roleRepo = roleRepo;
    }

    private static final String secret = "secret";

    public String extractUserName(String token){
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims,T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    public Boolean validateToken(String token , UserDetails userDetails){
        final String usernameWithRole = extractUserName(token);
        String[] roleAndName = usernameWithRole.split(" ");
        String username = roleAndName[1];
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public  String generateToken(UserDetails userDetails) throws Exception {
        Map<String,Object> claims = new HashMap<>();
//        UserRole userRole = userRoleRepo.findUserRoleByUserID(userRepo.findByNumber(userDetails.getUsername()).getId());
//        Role role =
        return createToken(claims,userDetails.getUsername());
    }

    private  String createToken(Map<String, Object> claims, String username) throws Exception {
        String role = getRoles(username);
        return Jwts.builder().setClaims(claims).setSubject(role+" "+username).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 60*60*10* 1000))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public String getRoles(String username) throws Exception {
        User user = userRepo.findByNumber(username);
        UserRole userRole = userRoleRepo.findUserRoleByUserId(user.getId());
        Role role = roleRepo.findById(userRole.getRole_id()).orElseThrow(()-> new Exception("NO user with given role id"));
        return role.getRole();
    }


}

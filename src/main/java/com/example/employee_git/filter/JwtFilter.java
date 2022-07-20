package com.example.employee_git.filter;



import com.example.employee_git.model.User;
import com.example.employee_git.repo.RoleRepo;
import com.example.employee_git.repo.UserRepo;
import com.example.employee_git.service.MyUserDetailsService;
import com.example.employee_git.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final MyUserDetailsService userDetailsService;

    private final JwtUtil jwtUtil;

    private final UserRepo userRepo;

    private final RoleRepo roleRepo;

    public JwtFilter(MyUserDetailsService userDetailsService, JwtUtil jwtUtil, UserRepo userRepo, RoleRepo roleRepo) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        String usernameWithAcess = null;
        String jwt = null;
        String username = null;
        String access = null;
        if (authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){
            jwt = authorizationHeader.substring(7);
            usernameWithAcess = jwtUtil.extractUserName(jwt);
            String[] roleAndName = usernameWithAcess.split(" ");
            username = roleAndName[1];
            access = roleAndName[0];
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (access!=null){
            authorities.add(new SimpleGrantedAuthority("ROLE_"+access));
        }

        if (username!=null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(jwtUtil.validateToken(jwt,userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,null,authorities
                );
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }


}
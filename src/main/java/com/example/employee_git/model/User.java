package com.example.employee_git.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    public String number;
    public String password;
    public long status;

    public User() {
    }

    public User(long id, String number, String password) {
        this.id = id;
        this.number = number;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    public String getPassword() {
        return password;
    }



    public void setPassword(String password) {
        this.password = password;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                '}';
    }
}

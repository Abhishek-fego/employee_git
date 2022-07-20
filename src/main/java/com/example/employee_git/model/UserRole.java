package com.example.employee_git.model;


import javax.persistence.*;

@Entity
@Table(name = "user_roles")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long role_id;
    private long user_id;

    public UserRole() {
    }

    public UserRole(long id, long role_id, long user_id) {
        this.id = id;
        this.role_id = role_id;
        this.user_id = user_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRole_id() {
        return role_id;
    }

    public void setRole_id(long role_id) {
        this.role_id = role_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "User_role{" +
                "id=" + id +
                ", role_id=" + role_id +
                ", user_id=" + user_id +
                '}';
    }
}

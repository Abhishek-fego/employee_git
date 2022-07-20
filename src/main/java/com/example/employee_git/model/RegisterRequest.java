package com.example.employee_git.model;


public class RegisterRequest {

    public String number;
    public String password;
    public String role;

    public RegisterRequest() {
    }

    public RegisterRequest(String number, String password, String role) {
        this.number = number;
        this.password = password;
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "RegisterRequest{" +
                "number='" + number + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
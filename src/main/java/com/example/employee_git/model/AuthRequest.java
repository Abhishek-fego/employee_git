package com.example.employee_git.model;


public class AuthRequest {

    public String number;
    public String password;

    public AuthRequest() {
    }

    public AuthRequest(String number, String password) {
        this.number = number;
        this.password = password;
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

    @Override
    public String toString() {
        return "AuthRequest{" +
                "number='" + number + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
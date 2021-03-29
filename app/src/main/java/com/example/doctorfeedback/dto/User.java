package com.example.doctorfeedback.dto;

public class User {
    public String username;
    public String emailAddress;
    public String role;
    public String department = ""; // For doctors

    public User() {}

    public User(String username, String emailAddress, String role, String department) {

        this.username = username;
        this.emailAddress = emailAddress;
        this.role = role;

        if(department != null && !department.isEmpty()) {
            this.department = department;
        }
    }
}

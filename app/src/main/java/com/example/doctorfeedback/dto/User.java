package com.example.doctorfeedback.dto;

public class User {

    public String id;
    public String username;
    public String emailAddress;
    public String role;

    // For doctors
    public String department = "";
    public double rate = 0;

    public User() {}

    public User(String username, String emailAddress, String role, String department, double rate) {

        this.username = username;
        this.emailAddress = emailAddress;
        this.role = role;
        this.rate = rate;

        if(department != null && !department.isEmpty()) {
            this.department = department;
        }
    }
}

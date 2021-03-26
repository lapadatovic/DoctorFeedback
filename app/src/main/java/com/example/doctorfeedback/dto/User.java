package com.example.doctorfeedback.dto;

public class User {
    public String userName;
    public String emailAddress;
    public String role;

    public User(){

    }

    public User(String userName, String emailAddress, String role){
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.role = role;
    }
}

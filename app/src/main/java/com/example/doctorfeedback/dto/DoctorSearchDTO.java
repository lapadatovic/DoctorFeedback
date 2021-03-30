package com.example.doctorfeedback.dto;

import java.io.Serializable;

public class DoctorSearchDTO implements Serializable {

    private String username;
    private String department;
    private String city;

    public DoctorSearchDTO(String username, String department, String city) {
        this.username = username;
        this.department = department;
        this.city = city;
    }

    public String getUsername() {
        return username;
    }

    public DoctorSearchDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getDepartment() {
        return department;
    }

    public DoctorSearchDTO setDepartment(String department) {
        this.department = department;
        return this;
    }

    public String getCity() {
        return city;
    }

    public DoctorSearchDTO setCity(String city) {
        this.city = city;
        return this;
    }
}

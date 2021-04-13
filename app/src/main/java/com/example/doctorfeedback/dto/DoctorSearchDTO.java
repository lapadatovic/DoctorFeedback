package com.example.doctorfeedback.dto;

import java.io.Serializable;

public class DoctorSearchDTO implements Serializable {

    private String username;
    private String department;
    private double distance;

    public DoctorSearchDTO(String username, String department, double distance) {
        this.username = username;
        this.department = department;
        this.distance = distance;
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

    public double getDistance() {
        return distance;
    }

    public DoctorSearchDTO setDistance(double distance) {
        this.distance = distance;
        return this;
    }
}

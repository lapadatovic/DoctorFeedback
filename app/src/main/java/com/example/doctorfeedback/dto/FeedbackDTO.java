package com.example.doctorfeedback.dto;

public class FeedbackDTO {

    public String headline;
    public String patientName;
    public String doctorID;
    public double rate;

    public FeedbackDTO() {}

    public FeedbackDTO(String doctorID, String headline, String patientName, double rate) {
        this.headline = headline;
        this.patientName = patientName;
        this.doctorID = doctorID;
        this.rate = rate;
    }
}

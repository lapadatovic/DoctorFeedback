package com.example.doctorfeedback.dto;

public class FeedbackDTO {

    private String headline;
    private String from;
    private Double rate;

    public FeedbackDTO(String headline, String from, Double rate) {
        this.headline = headline;
        this.from = from;
        this.rate = rate;
    }

    public String getHeadline() {
        return headline;
    }

    public FeedbackDTO setHeadline(String headline) {
        this.headline = headline;
        return this;
    }

    public String getFrom() {
        return from;
    }

    public FeedbackDTO setFrom(String from) {
        this.from = from;
        return this;
    }

    public Double getRate() {
        return rate;
    }

    public FeedbackDTO setRate(Double rate) {
        this.rate = rate;
        return this;
    }
}

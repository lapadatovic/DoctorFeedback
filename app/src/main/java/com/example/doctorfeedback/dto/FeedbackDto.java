package com.example.doctorfeedback.dto;

public class FeedbackDto {

    private String headline;
    private String from;
    private Double rate;

    public FeedbackDto(String headline, String from, Double rate) {
        this.headline = headline;
        this.from = from;
        this.rate = rate;
    }

    public String getHeadline() {
        return headline;
    }

    public FeedbackDto setHeadline(String headline) {
        this.headline = headline;
        return this;
    }

    public String getFrom() {
        return from;
    }

    public FeedbackDto setFrom(String from) {
        this.from = from;
        return this;
    }

    public Double getRate() {
        return rate;
    }

    public FeedbackDto setRate(Double rate) {
        this.rate = rate;
        return this;
    }
}

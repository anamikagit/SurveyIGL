package com.example.anamika.surveyigl.model;

public class LogoutResponce {

    public LogoutResponce() {
    }

    public LogoutResponce(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    private String response;
}

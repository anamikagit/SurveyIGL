package com.example.anamika.surveyigl.activity;

public class Data {

    private String label;
    private int imageResource;

    public Data() {
    }

    public Data(String label,int imageResource)
    {
        this.label = label;
        this.imageResource = imageResource;
    }

    public String getLabel() {
        return label;
    }
    public int getImageResource() {
        return imageResource;
    }
}

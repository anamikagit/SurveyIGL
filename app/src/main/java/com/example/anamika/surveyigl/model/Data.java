package com.example.anamika.surveyigl.model;

public class Data {

    private String label;
    private String display;
    private int imageResource;

    public Data() {
    }

    public Data(String label, String display, int imageResource)
    {
        this.label = label;
        this.display = display;
        this.imageResource = imageResource;
    }

    public String getLabel() {
        return label;
    }

    public String getDisplay() {
        return display;
    }

    public int getImageResource() {
        return imageResource;
    }
}

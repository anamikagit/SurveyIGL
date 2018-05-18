package com.example.anamika.surveyigl.activity;

public class MenuData {

    private String label;
    private String display;
    private int imageResource;

    public MenuData() {
    }

    public MenuData(String label, String display, int imageResource)
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

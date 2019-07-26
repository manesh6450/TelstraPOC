package com.wipro.architectureexample.model;

public class Note {
    private String title;
    private String description;
    private String imageHref;

    public Note(String title, String description, String imageHref) {
        this.title = title;
        this.description = description;
        this.imageHref = imageHref;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageHref() {
        return imageHref;
    }

}

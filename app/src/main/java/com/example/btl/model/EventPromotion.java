package com.example.btl.model;

public class EventPromotion {
    private int id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private String title;
    private String description;
    private String imageUrl;

    public EventPromotion(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;

    }

}
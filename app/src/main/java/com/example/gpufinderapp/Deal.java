package com.example.gpufinderapp;

public class Deal {

    private String name;
    private String price;
    private String url;
    private String imageUrl;

    public Deal(String name, String price, String url, String imageUrl) {
        this.name = name;
        this.price = price;
        this.url = url;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getUrl() {
        return url;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}

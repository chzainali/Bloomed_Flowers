package com.example.bloomedflowers.model;

import java.io.Serializable;

public class Flowers implements Serializable {
    int id;
    String image, name, type, season, growthRate, methodOfCare;

    public Flowers() {
    }

    public Flowers(String image, String name, String type, String season, String growthRate, String methodOfCare) {
        this.image = image;
        this.name = name;
        this.type = type;
        this.season = season;
        this.growthRate = growthRate;
        this.methodOfCare = methodOfCare;
    }

    public Flowers(int id, String image, String name, String type, String season, String growthRate, String methodOfCare) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.type = type;
        this.season = season;
        this.growthRate = growthRate;
        this.methodOfCare = methodOfCare;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getGrowthRate() {
        return growthRate;
    }

    public void setGrowthRate(String growthRate) {
        this.growthRate = growthRate;
    }

    public String getMethodOfCare() {
        return methodOfCare;
    }

    public void setMethodOfCare(String methodOfCare) {
        this.methodOfCare = methodOfCare;
    }
}

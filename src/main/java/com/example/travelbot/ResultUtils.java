package com.example.travelbot;

public class ResultUtils {

    private Object name;
    private Object type;
    private Object stars;
    private Object price;

    public ResultUtils(Object name, Object type, Object stars, Object price) {
        this.name = name;
        this.type = type;
        this.stars = stars;
        this.price = price;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    public Object getStars() {
        return stars;
    }

    public void setStars(Object stars) {
        this.stars = stars;
    }

    public Object getPrice() {
        return price;
    }

    public void setPrice(Object price) {
        this.price = price;
    }
}

package com.example.travelbot;

public class ReviewUtils {

    private Object name;
    private Object type;
    private Object text;

    public ReviewUtils(Object name, Object type, Object text) {
        this.name = name;
        this.type = type;
        this.text = text;

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

    public Object getText() {
        return text;
    }

    public void setText(Object text) {
        this.text = text;
    }


}

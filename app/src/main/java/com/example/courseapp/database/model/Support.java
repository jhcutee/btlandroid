package com.example.courseapp.database.model;

public class Support {
    private Integer id;
    private String type;
    private String address;
    private String time;
    private Double price;
    private String userId;
    private String note;

    public Support() {
    }

    public Support(Integer id, String type, String address, String time, Double price, String userId, String note) {
        this.id = id;
        this.type = type;
        this.address = address;
        this.time = time;
        this.price = price;
        this.userId = userId;
        this.note = note;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

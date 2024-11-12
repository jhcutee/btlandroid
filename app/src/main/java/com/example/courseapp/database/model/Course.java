package com.example.courseapp.database.model;

public class Course {
    private Integer id;
    private String name;
    private String courseImage;

    public Course() {
    }

    public Course(Integer id, String name, String courseImage) {
        this.id = id;
        this.name = name;
        this.courseImage = courseImage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourseImage() {
        return "file:///android_asset/" + courseImage;
    }

    public void setCourseImage(String courseImage) {
        this.courseImage = courseImage;
    }
}

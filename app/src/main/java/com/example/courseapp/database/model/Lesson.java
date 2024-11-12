package com.example.courseapp.database.model;

public class Lesson {
    private Integer id;
    private String name;
    private String content;
    private Integer courseId;

    public Lesson() {
    }

    public Lesson(Integer id, String name, String content, Integer courseId) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.courseId = courseId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }
}

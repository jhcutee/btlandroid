package com.example.courseapp.database.model;

public class TemporaryLoginInfo {
    private static TemporaryLoginInfo instance;
    private String username;
    private String password;

    private TemporaryLoginInfo() {
        // Private constructor to prevent instantiation
    }

    public static TemporaryLoginInfo getInstance() {
        if (instance == null) {
            instance = new TemporaryLoginInfo();
        }
        return instance;
    }

    public void setLoginInfo(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

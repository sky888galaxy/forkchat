package com.example.forkchat.model;

public class User {
    private String id;
    private String username;
    private String email;
    private String avatar;
    private String registrationDate;
    private String bio;

    public User() {
    }

    public User(String id, String username, String email, String avatar, String registrationDate, String bio) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.avatar = avatar;
        this.registrationDate = registrationDate;
        this.bio = bio;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }

    public String getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(String registrationDate) { this.registrationDate = registrationDate; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
}
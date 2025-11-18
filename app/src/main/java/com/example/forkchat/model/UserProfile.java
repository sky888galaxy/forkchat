package com.example.forkchat.model;

import java.util.List;

public class UserProfile {
    private User user;
    private List<Topic> topics;
    private List<Topic> participatedTopics;
    private List<Topic> collectedTopics;

    public UserProfile() {
    }

    public UserProfile(User user, List<Topic> topics, List<Topic> participatedTopics, List<Topic> collectedTopics) {
        this.user = user;
        this.topics = topics;
        this.participatedTopics = participatedTopics;
        this.collectedTopics = collectedTopics;
    }

    // Getters and Setters
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public List<Topic> getTopics() { return topics; }
    public void setTopics(List<Topic> topics) { this.topics = topics; }

    public List<Topic> getParticipatedTopics() { return participatedTopics; }
    public void setParticipatedTopics(List<Topic> participatedTopics) { this.participatedTopics = participatedTopics; }

    public List<Topic> getCollectedTopics() { return collectedTopics; }
    public void setCollectedTopics(List<Topic> collectedTopics) { this.collectedTopics = collectedTopics; }
}
package com.example.forkchat.model;

import java.util.List;

public class CreateTopicRequest {
    private String title;
    private String content;
    private List<String> tags;
    private String coverImage;

    public CreateTopicRequest(String title, String content, List<String> tags, String coverImage) {
        this.title = title;
        this.content = content;
        this.tags = tags;
        this.coverImage = coverImage;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

    public String getCoverImage() { return coverImage; }
    public void setCoverImage(String coverImage) { this.coverImage = coverImage; }
}
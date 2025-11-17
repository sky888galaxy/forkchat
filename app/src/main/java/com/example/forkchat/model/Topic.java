package com.example.forkchat.model;

import java.util.List;

public class Topic {
    private String id;
    private String title;
    private String content;
    private String authorId;
    private String authorName;
    private List<String> tags;
    private String createTime;
    private int likeCount;
    private int commentCount;
    private String coverImage;

    // 默认构造函数
    public Topic() {
    }

    // 带参数构造函数
    public Topic(String id, String title, String content, String authorId, String authorName,
                 List<String> tags, String createTime, int likeCount, int commentCount, String coverImage) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.authorName = authorName;
        this.tags = tags;
        this.createTime = createTime;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.coverImage = coverImage;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }
}
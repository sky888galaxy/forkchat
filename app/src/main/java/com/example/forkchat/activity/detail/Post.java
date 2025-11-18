package com.example.forkchat.activity.detail;

import java.util.List;

/**
 * 主帖模型 - 对应需求中的话题详情左侧展示内容
 */
public class Post {

    private final String id;
    private final String title;
    private final String body;
    private final String authorName;
    private final String authorAvatar;
    private final String publishTime;
    private final List<String> tags;
    private final int coverImageRes;
    private int viewCount;
    private int likeCount;
    private int supplementCount;
    private int questionCount;
    private boolean isLiked;
    private boolean isCollected;

    public Post(
            String id,
            String title,
            String body,
            String authorName,
            String authorAvatar,
            String publishTime,
            List<String> tags,
            int coverImageRes,
            int viewCount,
            int likeCount,
            int supplementCount,
            int questionCount,
            boolean isLiked,
            boolean isCollected
    ) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.authorName = authorName;
        this.authorAvatar = authorAvatar;
        this.publishTime = publishTime;
        this.tags = tags;
        this.coverImageRes = coverImageRes;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.supplementCount = supplementCount;
        this.questionCount = questionCount;
        this.isLiked = isLiked;
        this.isCollected = isCollected;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getAuthorAvatar() {
        return authorAvatar;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public List<String> getTags() {
        return tags;
    }

    public int getCoverImageRes() {
        return coverImageRes;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getSupplementCount() {
        return supplementCount;
    }

    public void setSupplementCount(int supplementCount) {
        this.supplementCount = supplementCount;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }
}

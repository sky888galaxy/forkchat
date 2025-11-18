package com.example.forkchat.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * 帖子实体类
 */
@Entity(tableName = "posts")
public class Post {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String title;
    private String content;
    private String coverImage;
    private long authorId;
    private String authorName;
    private String authorAvatar;
    private long createTime;
    private long lastReplyTime;
    private int viewCount;
    private int replyCount;
    private int likeCount;
    private String board; // 版块: 热门、最新、水区等
    private String tags; // 标签,用逗号分隔
    private boolean isDeleted;

    public Post() {
        this.createTime = System.currentTimeMillis();
        this.lastReplyTime = this.createTime;
        this.viewCount = 0;
        this.replyCount = 0;
        this.likeCount = 0;
        this.isDeleted = false;
    }

    @Ignore
    public Post(String title, String content, long authorId, String authorName, String board) {
        this();
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.authorName = authorName;
        this.board = board;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorAvatar() {
        return authorAvatar;
    }

    public void setAuthorAvatar(String authorAvatar) {
        this.authorAvatar = authorAvatar;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getLastReplyTime() {
        return lastReplyTime;
    }

    public void setLastReplyTime(long lastReplyTime) {
        this.lastReplyTime = lastReplyTime;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}


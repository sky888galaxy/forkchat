package com.example.forkchat.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * 收藏实体类
 */
@Entity(tableName = "favorites")
public class Favorite {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long userId;
    private long postId;
    private long createTime;

    public Favorite() {
        this.createTime = System.currentTimeMillis();
    }

    @Ignore
    public Favorite(long userId, long postId) {
        this();
        this.userId = userId;
        this.postId = postId;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}


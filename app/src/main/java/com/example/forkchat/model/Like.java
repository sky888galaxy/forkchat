package com.example.forkchat.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * 点赞实体类
 */
@Entity(tableName = "likes")
public class Like {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long userId;
    private long targetId; // 目标ID(帖子或回复的ID)
    private int targetType; // 目标类型: 1-帖子, 2-回复
    private long createTime;

    public static final int TYPE_POST = 1;
    public static final int TYPE_REPLY = 2;

    public Like() {
        this.createTime = System.currentTimeMillis();
    }

    @Ignore
    public Like(long userId, long targetId, int targetType) {
        this();
        this.userId = userId;
        this.targetId = targetId;
        this.targetType = targetType;
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

    public long getTargetId() {
        return targetId;
    }

    public void setTargetId(long targetId) {
        this.targetId = targetId;
    }

    public int getTargetType() {
        return targetType;
    }

    public void setTargetType(int targetType) {
        this.targetType = targetType;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}


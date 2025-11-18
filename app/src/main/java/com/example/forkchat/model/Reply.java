package com.example.forkchat.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * 回复实体类
 * 支持主回复和楼中楼(二级回复)
 */
@Entity(tableName = "replies")
public class Reply {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long postId; // 所属帖子ID
    private long parentReplyId; // 父回复ID,如果为0表示主回复,否则为二级回复
    private String content;
    private long authorId;
    private String authorName;
    private String authorAvatar;
    private long replyToUserId; // 回复给谁的用户ID
    private String replyToUserName; // 回复给谁的用户名
    private long createTime;
    private int likeCount;
    private int floor; // 楼层号
    private boolean isDeleted;

    public Reply() {
        this.createTime = System.currentTimeMillis();
        this.likeCount = 0;
        this.parentReplyId = 0;
        this.isDeleted = false;
    }

    @Ignore
    public Reply(long postId, String content, long authorId, String authorName) {
        this();
        this.postId = postId;
        this.content = content;
        this.authorId = authorId;
        this.authorName = authorName;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public long getParentReplyId() {
        return parentReplyId;
    }

    public void setParentReplyId(long parentReplyId) {
        this.parentReplyId = parentReplyId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public long getReplyToUserId() {
        return replyToUserId;
    }

    public void setReplyToUserId(long replyToUserId) {
        this.replyToUserId = replyToUserId;
    }

    public String getReplyToUserName() {
        return replyToUserName;
    }

    public void setReplyToUserName(String replyToUserName) {
        this.replyToUserName = replyToUserName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}


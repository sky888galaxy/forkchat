package com.example.forkchat.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * 标签实体类
 */
@Entity(tableName = "tags")
public class Tag {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String color;
    private int postCount;

    public Tag() {
    }

    @Ignore
    public Tag(String name, String color) {
        this.name = name;
        this.color = color;
        this.postCount = 0;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }
}


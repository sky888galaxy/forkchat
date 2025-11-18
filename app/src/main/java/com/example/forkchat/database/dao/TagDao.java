package com.example.forkchat.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.forkchat.model.Tag;

import java.util.List;

@Dao
public interface TagDao {
    @Insert
    long insert(Tag tag);

    @Update
    void update(Tag tag);

    @Delete
    void delete(Tag tag);

    @Query("SELECT * FROM tags WHERE id = :tagId")
    LiveData<Tag> getTagById(long tagId);

    @Query("SELECT * FROM tags WHERE name = :name LIMIT 1")
    Tag getTagByName(String name);

    @Query("SELECT * FROM tags ORDER BY postCount DESC")
    LiveData<List<Tag>> getAllTagsByPopularity();

    @Query("SELECT * FROM tags ORDER BY name ASC")
    LiveData<List<Tag>> getAllTagsByName();

    @Query("UPDATE tags SET postCount = postCount + 1 WHERE id = :tagId")
    void incrementPostCount(long tagId);
}


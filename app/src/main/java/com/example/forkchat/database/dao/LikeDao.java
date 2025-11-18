package com.example.forkchat.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.forkchat.model.Like;

import java.util.List;

/**
 * 点赞数据访问对象
 */
@Dao
public interface LikeDao {
    @Insert
    long insert(Like like);

    @Delete
    void delete(Like like);

    @Query("SELECT * FROM likes WHERE userId = :userId AND targetId = :targetId AND targetType = :targetType LIMIT 1")
    Like getLike(long userId, long targetId, int targetType);

    @Query("DELETE FROM likes WHERE userId = :userId AND targetId = :targetId AND targetType = :targetType")
    void deleteLike(long userId, long targetId, int targetType);

    @Query("SELECT COUNT(*) FROM likes WHERE targetId = :targetId AND targetType = :targetType")
    int getLikeCount(long targetId, int targetType);

    @Query("SELECT EXISTS(SELECT 1 FROM likes WHERE userId = :userId AND targetId = :targetId AND targetType = :targetType)")
    LiveData<Boolean> isLiked(long userId, long targetId, int targetType);

    @Query("SELECT * FROM likes WHERE userId = :userId ORDER BY createTime DESC")
    LiveData<List<Like>> getLikesByUser(long userId);
}


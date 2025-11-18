package com.example.forkchat.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.forkchat.model.Favorite;

import java.util.List;

@Dao
public interface FavoriteDao {
    @Insert
    long insert(Favorite favorite);

    @Delete
    void delete(Favorite favorite);

    @Query("SELECT * FROM favorites WHERE userId = :userId ORDER BY createTime DESC")
    LiveData<List<Favorite>> getFavoritesByUser(long userId);

    @Query("SELECT * FROM favorites WHERE userId = :userId AND postId = :postId LIMIT 1")
    Favorite getFavorite(long userId, long postId);

    @Query("DELETE FROM favorites WHERE userId = :userId AND postId = :postId")
    void deleteFavorite(long userId, long postId);

    @Query("SELECT COUNT(*) FROM favorites WHERE postId = :postId")
    int getFavoriteCountByPostId(long postId);

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE userId = :userId AND postId = :postId)")
    LiveData<Boolean> isFavorited(long userId, long postId);
}


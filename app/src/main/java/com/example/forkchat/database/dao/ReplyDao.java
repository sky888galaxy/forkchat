package com.example.forkchat.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.forkchat.model.Reply;

import java.util.List;

/**
 * 回复数据访问对象
 */
@Dao
public interface ReplyDao {
    @Insert
    long insert(Reply reply);

    @Update
    void update(Reply reply);

    @Delete
    void delete(Reply reply);

    @Query("SELECT * FROM replies WHERE id = :replyId")
    LiveData<Reply> getReplyById(long replyId);

    @Query("SELECT * FROM replies WHERE postId = :postId AND isDeleted = 0 ORDER BY createTime ASC")
    LiveData<List<Reply>> getRepliesByPostId(long postId);

    @Query("SELECT * FROM replies WHERE postId = :postId AND parentReplyId = 0 AND isDeleted = 0 ORDER BY createTime ASC")
    LiveData<List<Reply>> getMainRepliesByPostId(long postId);

    @Query("SELECT * FROM replies WHERE parentReplyId = :parentReplyId AND isDeleted = 0 ORDER BY createTime ASC")
    LiveData<List<Reply>> getSubRepliesByParentId(long parentReplyId);

    @Query("SELECT * FROM replies WHERE authorId = :authorId AND isDeleted = 0 ORDER BY createTime DESC")
    LiveData<List<Reply>> getRepliesByAuthor(long authorId);

    @Query("SELECT MAX(floor) FROM replies WHERE postId = :postId")
    int getMaxFloor(long postId);

    @Query("UPDATE replies SET likeCount = :likeCount WHERE id = :replyId")
    void updateLikeCount(long replyId, int likeCount);

    @Query("UPDATE replies SET isDeleted = 1 WHERE id = :replyId")
    void softDelete(long replyId);

    @Query("SELECT COUNT(*) FROM replies WHERE postId = :postId AND isDeleted = 0")
    int getReplyCountByPostId(long postId);
}


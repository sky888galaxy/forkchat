package com.example.forkchat.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.forkchat.model.Post;

import java.util.List;

/**
 * 帖子数据访问对象
 */
@Dao
public interface PostDao {
    @Insert
    long insert(Post post);

    @Update
    void update(Post post);

    @Delete
    void delete(Post post);

    @Query("SELECT * FROM posts WHERE id = :postId AND isDeleted = 0")
    LiveData<Post> getPostById(long postId);

    @Query("SELECT * FROM posts WHERE isDeleted = 0 ORDER BY createTime DESC")
    LiveData<List<Post>> getAllPostsByCreateTime();

    @Query("SELECT * FROM posts WHERE isDeleted = 0 ORDER BY lastReplyTime DESC")
    LiveData<List<Post>> getAllPostsByReplyTime();

    @Query("SELECT * FROM posts WHERE board = :board AND isDeleted = 0 ORDER BY createTime DESC")
    LiveData<List<Post>> getPostsByBoard(String board);

    @Query("SELECT * FROM posts WHERE authorId = :authorId AND isDeleted = 0 ORDER BY createTime DESC")
    LiveData<List<Post>> getPostsByAuthor(long authorId);

    @Query("SELECT * FROM posts WHERE tags LIKE :tag AND isDeleted = 0 ORDER BY createTime DESC")
    LiveData<List<Post>> getPostsByTag(String tag);

    @Query("UPDATE posts SET viewCount = viewCount + 1 WHERE id = :postId")
    void incrementViewCount(long postId);

    @Query("UPDATE posts SET replyCount = replyCount + 1, lastReplyTime = :replyTime WHERE id = :postId")
    void incrementReplyCount(long postId, long replyTime);

    @Query("UPDATE posts SET likeCount = :likeCount WHERE id = :postId")
    void updateLikeCount(long postId, int likeCount);

    @Query("UPDATE posts SET isDeleted = 1 WHERE id = :postId")
    void softDelete(long postId);
}


package com.example.forkchat.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.forkchat.database.TiebaDatabase;
import com.example.forkchat.database.dao.PostDao;
import com.example.forkchat.model.Post;

import java.util.List;

/**
 * 帖子仓库
 */
public class PostRepository {
    private PostDao postDao;

    public PostRepository(Application application) {
        TiebaDatabase database = TiebaDatabase.getDatabase(application);
        postDao = database.postDao();
    }

    public LiveData<List<Post>> getAllPostsByCreateTime() {
        return postDao.getAllPostsByCreateTime();
    }

    public LiveData<List<Post>> getAllPostsByReplyTime() {
        return postDao.getAllPostsByReplyTime();
    }

    public LiveData<List<Post>> getPostsByBoard(String board) {
        return postDao.getPostsByBoard(board);
    }

    public LiveData<List<Post>> getPostsByTag(String tag) {
        return postDao.getPostsByTag("%" + tag + "%");
    }

    public LiveData<Post> getPostById(long postId) {
        return postDao.getPostById(postId);
    }

    public void insertPost(Post post, OnPostInsertedListener listener) {
        TiebaDatabase.databaseWriteExecutor.execute(() -> {
            long id = postDao.insert(post);
            if (listener != null) {
                listener.onPostInserted(id);
            }
        });
    }

    public void updatePost(Post post) {
        TiebaDatabase.databaseWriteExecutor.execute(() -> {
            postDao.update(post);
        });
    }

    public void deletePost(long postId) {
        TiebaDatabase.databaseWriteExecutor.execute(() -> {
            postDao.softDelete(postId);
        });
    }

    public void incrementViewCount(long postId) {
        TiebaDatabase.databaseWriteExecutor.execute(() -> {
            postDao.incrementViewCount(postId);
        });
    }

    public void incrementReplyCount(long postId) {
        TiebaDatabase.databaseWriteExecutor.execute(() -> {
            postDao.incrementReplyCount(postId, System.currentTimeMillis());
        });
    }

    public void updateLikeCount(long postId, int likeCount) {
        TiebaDatabase.databaseWriteExecutor.execute(() -> {
            postDao.updateLikeCount(postId, likeCount);
        });
    }

    public interface OnPostInsertedListener {
        void onPostInserted(long postId);
    }
}


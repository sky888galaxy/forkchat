package com.example.forkchat.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.forkchat.database.TiebaDatabase;
import com.example.forkchat.database.dao.LikeDao;
import com.example.forkchat.model.Like;

/**
 * 点赞仓库
 */
public class LikeRepository {
    private LikeDao likeDao;

    public LikeRepository(Application application) {
        TiebaDatabase database = TiebaDatabase.getDatabase(application);
        likeDao = database.likeDao();
    }

    public LiveData<Boolean> isLiked(long userId, long targetId, int targetType) {
        return likeDao.isLiked(userId, targetId, targetType);
    }

    public void addLike(long userId, long targetId, int targetType, OnLikeChangedListener listener) {
        TiebaDatabase.databaseWriteExecutor.execute(() -> {
            Like like = new Like(userId, targetId, targetType);
            likeDao.insert(like);
            
            int count = likeDao.getLikeCount(targetId, targetType);
            if (listener != null) {
                listener.onLikeChanged(count);
            }
        });
    }

    public void removeLike(long userId, long targetId, int targetType, OnLikeChangedListener listener) {
        TiebaDatabase.databaseWriteExecutor.execute(() -> {
            likeDao.deleteLike(userId, targetId, targetType);
            
            int count = likeDao.getLikeCount(targetId, targetType);
            if (listener != null) {
                listener.onLikeChanged(count);
            }
        });
    }

    public void getLikeCount(long targetId, int targetType, OnLikeCountListener listener) {
        TiebaDatabase.databaseWriteExecutor.execute(() -> {
            int count = likeDao.getLikeCount(targetId, targetType);
            if (listener != null) {
                listener.onLikeCountRetrieved(count);
            }
        });
    }

    public interface OnLikeChangedListener {
        void onLikeChanged(int newCount);
    }

    public interface OnLikeCountListener {
        void onLikeCountRetrieved(int count);
    }
}


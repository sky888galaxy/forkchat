package com.example.forkchat.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;


import com.example.forkchat.database.TiebaDatabase;
import com.example.forkchat.database.dao.FavoriteDao;
import com.example.forkchat.model.Favorite;

import java.util.List;

/**
 * 收藏仓库
 */
public class FavoriteRepository {
    private FavoriteDao favoriteDao;

    public FavoriteRepository(Application application) {
        TiebaDatabase database = TiebaDatabase.getDatabase(application);
        favoriteDao = database.favoriteDao();
    }

    public LiveData<List<Favorite>> getFavoritesByUser(long userId) {
        return favoriteDao.getFavoritesByUser(userId);
    }

    public LiveData<Boolean> isFavorited(long userId, long postId) {
        return favoriteDao.isFavorited(userId, postId);
    }

    public void addFavorite(long userId, long postId) {
        TiebaDatabase.databaseWriteExecutor.execute(() -> {
            Favorite favorite = new Favorite(userId, postId);
            favoriteDao.insert(favorite);
        });
    }

    public void removeFavorite(long userId, long postId) {
        TiebaDatabase.databaseWriteExecutor.execute(() -> {
            favoriteDao.deleteFavorite(userId, postId);
        });
    }
}


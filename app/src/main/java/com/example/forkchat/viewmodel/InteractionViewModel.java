package com.example.forkchat.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.example.forkchat.model.Favorite;
import com.example.forkchat.repository.FavoriteRepository;
import com.example.forkchat.repository.LikeRepository;

import java.util.List;

/**
 * 互动ViewModel (点赞、收藏)
 */
public class InteractionViewModel extends AndroidViewModel {
    private LikeRepository likeRepository;
    private FavoriteRepository favoriteRepository;

    public InteractionViewModel(@NonNull Application application) {
        super(application);
        likeRepository = new LikeRepository(application);
        favoriteRepository = new FavoriteRepository(application);
    }

    // 点赞相关
    public LiveData<Boolean> isLiked(long userId, long targetId, int targetType) {
        return likeRepository.isLiked(userId, targetId, targetType);
    }

    public void addLike(long userId, long targetId, int targetType, LikeRepository.OnLikeChangedListener listener) {
        likeRepository.addLike(userId, targetId, targetType, listener);
    }

    public void removeLike(long userId, long targetId, int targetType, LikeRepository.OnLikeChangedListener listener) {
        likeRepository.removeLike(userId, targetId, targetType, listener);
    }

    public void getLikeCount(long targetId, int targetType, LikeRepository.OnLikeCountListener listener) {
        likeRepository.getLikeCount(targetId, targetType, listener);
    }

    // 收藏相关
    public LiveData<Boolean> isFavorited(long userId, long postId) {
        return favoriteRepository.isFavorited(userId, postId);
    }

    public void addFavorite(long userId, long postId) {
        favoriteRepository.addFavorite(userId, postId);
    }

    public void removeFavorite(long userId, long postId) {
        favoriteRepository.removeFavorite(userId, postId);
    }

    public LiveData<List<Favorite>> getFavoritesByUser(long userId) {
        return favoriteRepository.getFavoritesByUser(userId);
    }
}


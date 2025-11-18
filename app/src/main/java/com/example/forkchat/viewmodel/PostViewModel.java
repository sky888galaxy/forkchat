package com.example.forkchat.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.forkchat.model.Post;
import com.example.forkchat.repository.PostRepository;

import java.util.List;

/**
 * 帖子ViewModel
 */
public class PostViewModel extends AndroidViewModel {
    private PostRepository repository;
    private LiveData<List<Post>> allPostsByCreateTime;
    private LiveData<List<Post>> allPostsByReplyTime;
    private MutableLiveData<String> currentBoard = new MutableLiveData<>();
    private MutableLiveData<String> currentTag = new MutableLiveData<>();
    private MutableLiveData<String> sortOrder = new MutableLiveData<>("create_time"); // create_time or reply_time

    public PostViewModel(@NonNull Application application) {
        super(application);
        repository = new PostRepository(application);
        allPostsByCreateTime = repository.getAllPostsByCreateTime();
        allPostsByReplyTime = repository.getAllPostsByReplyTime();
    }

    public LiveData<List<Post>> getAllPostsByCreateTime() {
        return allPostsByCreateTime;
    }

    public LiveData<List<Post>> getAllPostsByReplyTime() {
        return allPostsByReplyTime;
    }

    public LiveData<List<Post>> getPostsByBoard(String board) {
        return repository.getPostsByBoard(board);
    }

    public LiveData<List<Post>> getPostsByTag(String tag) {
        return repository.getPostsByTag(tag);
    }

    public LiveData<Post> getPostById(long postId) {
        return repository.getPostById(postId);
    }

    public void insertPost(Post post, PostRepository.OnPostInsertedListener listener) {
        repository.insertPost(post, listener);
    }

    public void updatePost(Post post) {
        repository.updatePost(post);
    }

    public void deletePost(long postId) {
        repository.deletePost(postId);
    }

    public void incrementViewCount(long postId) {
        repository.incrementViewCount(postId);
    }

    public void incrementReplyCount(long postId) {
        repository.incrementReplyCount(postId);
    }

    public void updateLikeCount(long postId, int likeCount) {
        repository.updateLikeCount(postId, likeCount);
    }

    public MutableLiveData<String> getCurrentBoard() {
        return currentBoard;
    }

    public void setCurrentBoard(String board) {
        currentBoard.setValue(board);
    }

    public MutableLiveData<String> getCurrentTag() {
        return currentTag;
    }

    public void setCurrentTag(String tag) {
        currentTag.setValue(tag);
    }

    public MutableLiveData<String> getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String order) {
        sortOrder.setValue(order);
    }
}


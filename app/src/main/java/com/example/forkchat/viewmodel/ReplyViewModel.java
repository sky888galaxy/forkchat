package com.example.forkchat.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.example.forkchat.model.Reply;
import com.example.forkchat.repository.ReplyRepository;

import java.util.List;

/**
 * 回复ViewModel
 */
public class ReplyViewModel extends AndroidViewModel {
    private ReplyRepository repository;

    public ReplyViewModel(@NonNull Application application) {
        super(application);
        repository = new ReplyRepository(application);
    }

    public LiveData<List<Reply>> getRepliesByPostId(long postId) {
        return repository.getRepliesByPostId(postId);
    }

    public LiveData<List<Reply>> getMainRepliesByPostId(long postId) {
        return repository.getMainRepliesByPostId(postId);
    }

    public LiveData<List<Reply>> getSubRepliesByParentId(long parentReplyId) {
        return repository.getSubRepliesByParentId(parentReplyId);
    }

    public void insertReply(Reply reply, ReplyRepository.OnReplyInsertedListener listener) {
        repository.insertReply(reply, listener);
    }

    public void updateReply(Reply reply) {
        repository.updateReply(reply);
    }

    public void deleteReply(long replyId) {
        repository.deleteReply(replyId);
    }

    public void updateLikeCount(long replyId, int likeCount) {
        repository.updateLikeCount(replyId, likeCount);
    }
}


package com.example.forkchat.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.forkchat.database.TiebaDatabase;
import com.example.forkchat.database.dao.ReplyDao;
import com.example.forkchat.model.Reply;

import java.util.List;

/**
 * 回复仓库
 */
public class ReplyRepository {
    private ReplyDao replyDao;

    public ReplyRepository(Application application) {
        TiebaDatabase database = TiebaDatabase.getDatabase(application);
        replyDao = database.replyDao();
    }

    public LiveData<List<Reply>> getRepliesByPostId(long postId) {
        return replyDao.getRepliesByPostId(postId);
    }

    public LiveData<List<Reply>> getMainRepliesByPostId(long postId) {
        return replyDao.getMainRepliesByPostId(postId);
    }

    public LiveData<List<Reply>> getSubRepliesByParentId(long parentReplyId) {
        return replyDao.getSubRepliesByParentId(parentReplyId);
    }

    public void insertReply(Reply reply, OnReplyInsertedListener listener) {
        TiebaDatabase.databaseWriteExecutor.execute(() -> {
            // 获取楼层号
            int maxFloor = replyDao.getMaxFloor(reply.getPostId());
            reply.setFloor(maxFloor + 1);
            
            long id = replyDao.insert(reply);
            if (listener != null) {
                listener.onReplyInserted(id);
            }
        });
    }

    public void updateReply(Reply reply) {
        TiebaDatabase.databaseWriteExecutor.execute(() -> {
            replyDao.update(reply);
        });
    }

    public void deleteReply(long replyId) {
        TiebaDatabase.databaseWriteExecutor.execute(() -> {
            replyDao.softDelete(replyId);
        });
    }

    public void updateLikeCount(long replyId, int likeCount) {
        TiebaDatabase.databaseWriteExecutor.execute(() -> {
            replyDao.updateLikeCount(replyId, likeCount);
        });
    }

    public interface OnReplyInsertedListener {
        void onReplyInserted(long replyId);
    }
}


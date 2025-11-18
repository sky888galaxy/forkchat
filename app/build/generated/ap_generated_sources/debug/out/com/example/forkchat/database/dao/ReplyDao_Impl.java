package com.example.forkchat.database.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.forkchat.model.Reply;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ReplyDao_Impl implements ReplyDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Reply> __insertionAdapterOfReply;

  private final EntityDeletionOrUpdateAdapter<Reply> __deletionAdapterOfReply;

  private final EntityDeletionOrUpdateAdapter<Reply> __updateAdapterOfReply;

  private final SharedSQLiteStatement __preparedStmtOfUpdateLikeCount;

  private final SharedSQLiteStatement __preparedStmtOfSoftDelete;

  public ReplyDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfReply = new EntityInsertionAdapter<Reply>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `replies` (`id`,`postId`,`parentReplyId`,`content`,`authorId`,`authorName`,`authorAvatar`,`replyToUserId`,`replyToUserName`,`createTime`,`likeCount`,`floor`,`isDeleted`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Reply entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getPostId());
        statement.bindLong(3, entity.getParentReplyId());
        if (entity.getContent() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getContent());
        }
        statement.bindLong(5, entity.getAuthorId());
        if (entity.getAuthorName() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getAuthorName());
        }
        if (entity.getAuthorAvatar() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getAuthorAvatar());
        }
        statement.bindLong(8, entity.getReplyToUserId());
        if (entity.getReplyToUserName() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getReplyToUserName());
        }
        statement.bindLong(10, entity.getCreateTime());
        statement.bindLong(11, entity.getLikeCount());
        statement.bindLong(12, entity.getFloor());
        final int _tmp = entity.isDeleted() ? 1 : 0;
        statement.bindLong(13, _tmp);
      }
    };
    this.__deletionAdapterOfReply = new EntityDeletionOrUpdateAdapter<Reply>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `replies` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Reply entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfReply = new EntityDeletionOrUpdateAdapter<Reply>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `replies` SET `id` = ?,`postId` = ?,`parentReplyId` = ?,`content` = ?,`authorId` = ?,`authorName` = ?,`authorAvatar` = ?,`replyToUserId` = ?,`replyToUserName` = ?,`createTime` = ?,`likeCount` = ?,`floor` = ?,`isDeleted` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Reply entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getPostId());
        statement.bindLong(3, entity.getParentReplyId());
        if (entity.getContent() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getContent());
        }
        statement.bindLong(5, entity.getAuthorId());
        if (entity.getAuthorName() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getAuthorName());
        }
        if (entity.getAuthorAvatar() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getAuthorAvatar());
        }
        statement.bindLong(8, entity.getReplyToUserId());
        if (entity.getReplyToUserName() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getReplyToUserName());
        }
        statement.bindLong(10, entity.getCreateTime());
        statement.bindLong(11, entity.getLikeCount());
        statement.bindLong(12, entity.getFloor());
        final int _tmp = entity.isDeleted() ? 1 : 0;
        statement.bindLong(13, _tmp);
        statement.bindLong(14, entity.getId());
      }
    };
    this.__preparedStmtOfUpdateLikeCount = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE replies SET likeCount = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfSoftDelete = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE replies SET isDeleted = 1 WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public long insert(final Reply reply) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfReply.insertAndReturnId(reply);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Reply reply) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfReply.handle(reply);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Reply reply) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfReply.handle(reply);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateLikeCount(final long replyId, final int likeCount) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateLikeCount.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, likeCount);
    _argIndex = 2;
    _stmt.bindLong(_argIndex, replyId);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfUpdateLikeCount.release(_stmt);
    }
  }

  @Override
  public void softDelete(final long replyId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfSoftDelete.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, replyId);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfSoftDelete.release(_stmt);
    }
  }

  @Override
  public LiveData<Reply> getReplyById(final long replyId) {
    final String _sql = "SELECT * FROM replies WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, replyId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"replies"}, false, new Callable<Reply>() {
      @Override
      @Nullable
      public Reply call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPostId = CursorUtil.getColumnIndexOrThrow(_cursor, "postId");
          final int _cursorIndexOfParentReplyId = CursorUtil.getColumnIndexOrThrow(_cursor, "parentReplyId");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfAuthorId = CursorUtil.getColumnIndexOrThrow(_cursor, "authorId");
          final int _cursorIndexOfAuthorName = CursorUtil.getColumnIndexOrThrow(_cursor, "authorName");
          final int _cursorIndexOfAuthorAvatar = CursorUtil.getColumnIndexOrThrow(_cursor, "authorAvatar");
          final int _cursorIndexOfReplyToUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "replyToUserId");
          final int _cursorIndexOfReplyToUserName = CursorUtil.getColumnIndexOrThrow(_cursor, "replyToUserName");
          final int _cursorIndexOfCreateTime = CursorUtil.getColumnIndexOrThrow(_cursor, "createTime");
          final int _cursorIndexOfLikeCount = CursorUtil.getColumnIndexOrThrow(_cursor, "likeCount");
          final int _cursorIndexOfFloor = CursorUtil.getColumnIndexOrThrow(_cursor, "floor");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isDeleted");
          final Reply _result;
          if (_cursor.moveToFirst()) {
            _result = new Reply();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _result.setId(_tmpId);
            final long _tmpPostId;
            _tmpPostId = _cursor.getLong(_cursorIndexOfPostId);
            _result.setPostId(_tmpPostId);
            final long _tmpParentReplyId;
            _tmpParentReplyId = _cursor.getLong(_cursorIndexOfParentReplyId);
            _result.setParentReplyId(_tmpParentReplyId);
            final String _tmpContent;
            if (_cursor.isNull(_cursorIndexOfContent)) {
              _tmpContent = null;
            } else {
              _tmpContent = _cursor.getString(_cursorIndexOfContent);
            }
            _result.setContent(_tmpContent);
            final long _tmpAuthorId;
            _tmpAuthorId = _cursor.getLong(_cursorIndexOfAuthorId);
            _result.setAuthorId(_tmpAuthorId);
            final String _tmpAuthorName;
            if (_cursor.isNull(_cursorIndexOfAuthorName)) {
              _tmpAuthorName = null;
            } else {
              _tmpAuthorName = _cursor.getString(_cursorIndexOfAuthorName);
            }
            _result.setAuthorName(_tmpAuthorName);
            final String _tmpAuthorAvatar;
            if (_cursor.isNull(_cursorIndexOfAuthorAvatar)) {
              _tmpAuthorAvatar = null;
            } else {
              _tmpAuthorAvatar = _cursor.getString(_cursorIndexOfAuthorAvatar);
            }
            _result.setAuthorAvatar(_tmpAuthorAvatar);
            final long _tmpReplyToUserId;
            _tmpReplyToUserId = _cursor.getLong(_cursorIndexOfReplyToUserId);
            _result.setReplyToUserId(_tmpReplyToUserId);
            final String _tmpReplyToUserName;
            if (_cursor.isNull(_cursorIndexOfReplyToUserName)) {
              _tmpReplyToUserName = null;
            } else {
              _tmpReplyToUserName = _cursor.getString(_cursorIndexOfReplyToUserName);
            }
            _result.setReplyToUserName(_tmpReplyToUserName);
            final long _tmpCreateTime;
            _tmpCreateTime = _cursor.getLong(_cursorIndexOfCreateTime);
            _result.setCreateTime(_tmpCreateTime);
            final int _tmpLikeCount;
            _tmpLikeCount = _cursor.getInt(_cursorIndexOfLikeCount);
            _result.setLikeCount(_tmpLikeCount);
            final int _tmpFloor;
            _tmpFloor = _cursor.getInt(_cursorIndexOfFloor);
            _result.setFloor(_tmpFloor);
            final boolean _tmpIsDeleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp != 0;
            _result.setDeleted(_tmpIsDeleted);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Reply>> getRepliesByPostId(final long postId) {
    final String _sql = "SELECT * FROM replies WHERE postId = ? AND isDeleted = 0 ORDER BY createTime ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, postId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"replies"}, false, new Callable<List<Reply>>() {
      @Override
      @Nullable
      public List<Reply> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPostId = CursorUtil.getColumnIndexOrThrow(_cursor, "postId");
          final int _cursorIndexOfParentReplyId = CursorUtil.getColumnIndexOrThrow(_cursor, "parentReplyId");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfAuthorId = CursorUtil.getColumnIndexOrThrow(_cursor, "authorId");
          final int _cursorIndexOfAuthorName = CursorUtil.getColumnIndexOrThrow(_cursor, "authorName");
          final int _cursorIndexOfAuthorAvatar = CursorUtil.getColumnIndexOrThrow(_cursor, "authorAvatar");
          final int _cursorIndexOfReplyToUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "replyToUserId");
          final int _cursorIndexOfReplyToUserName = CursorUtil.getColumnIndexOrThrow(_cursor, "replyToUserName");
          final int _cursorIndexOfCreateTime = CursorUtil.getColumnIndexOrThrow(_cursor, "createTime");
          final int _cursorIndexOfLikeCount = CursorUtil.getColumnIndexOrThrow(_cursor, "likeCount");
          final int _cursorIndexOfFloor = CursorUtil.getColumnIndexOrThrow(_cursor, "floor");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isDeleted");
          final List<Reply> _result = new ArrayList<Reply>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Reply _item;
            _item = new Reply();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final long _tmpPostId;
            _tmpPostId = _cursor.getLong(_cursorIndexOfPostId);
            _item.setPostId(_tmpPostId);
            final long _tmpParentReplyId;
            _tmpParentReplyId = _cursor.getLong(_cursorIndexOfParentReplyId);
            _item.setParentReplyId(_tmpParentReplyId);
            final String _tmpContent;
            if (_cursor.isNull(_cursorIndexOfContent)) {
              _tmpContent = null;
            } else {
              _tmpContent = _cursor.getString(_cursorIndexOfContent);
            }
            _item.setContent(_tmpContent);
            final long _tmpAuthorId;
            _tmpAuthorId = _cursor.getLong(_cursorIndexOfAuthorId);
            _item.setAuthorId(_tmpAuthorId);
            final String _tmpAuthorName;
            if (_cursor.isNull(_cursorIndexOfAuthorName)) {
              _tmpAuthorName = null;
            } else {
              _tmpAuthorName = _cursor.getString(_cursorIndexOfAuthorName);
            }
            _item.setAuthorName(_tmpAuthorName);
            final String _tmpAuthorAvatar;
            if (_cursor.isNull(_cursorIndexOfAuthorAvatar)) {
              _tmpAuthorAvatar = null;
            } else {
              _tmpAuthorAvatar = _cursor.getString(_cursorIndexOfAuthorAvatar);
            }
            _item.setAuthorAvatar(_tmpAuthorAvatar);
            final long _tmpReplyToUserId;
            _tmpReplyToUserId = _cursor.getLong(_cursorIndexOfReplyToUserId);
            _item.setReplyToUserId(_tmpReplyToUserId);
            final String _tmpReplyToUserName;
            if (_cursor.isNull(_cursorIndexOfReplyToUserName)) {
              _tmpReplyToUserName = null;
            } else {
              _tmpReplyToUserName = _cursor.getString(_cursorIndexOfReplyToUserName);
            }
            _item.setReplyToUserName(_tmpReplyToUserName);
            final long _tmpCreateTime;
            _tmpCreateTime = _cursor.getLong(_cursorIndexOfCreateTime);
            _item.setCreateTime(_tmpCreateTime);
            final int _tmpLikeCount;
            _tmpLikeCount = _cursor.getInt(_cursorIndexOfLikeCount);
            _item.setLikeCount(_tmpLikeCount);
            final int _tmpFloor;
            _tmpFloor = _cursor.getInt(_cursorIndexOfFloor);
            _item.setFloor(_tmpFloor);
            final boolean _tmpIsDeleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp != 0;
            _item.setDeleted(_tmpIsDeleted);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Reply>> getMainRepliesByPostId(final long postId) {
    final String _sql = "SELECT * FROM replies WHERE postId = ? AND parentReplyId = 0 AND isDeleted = 0 ORDER BY createTime ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, postId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"replies"}, false, new Callable<List<Reply>>() {
      @Override
      @Nullable
      public List<Reply> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPostId = CursorUtil.getColumnIndexOrThrow(_cursor, "postId");
          final int _cursorIndexOfParentReplyId = CursorUtil.getColumnIndexOrThrow(_cursor, "parentReplyId");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfAuthorId = CursorUtil.getColumnIndexOrThrow(_cursor, "authorId");
          final int _cursorIndexOfAuthorName = CursorUtil.getColumnIndexOrThrow(_cursor, "authorName");
          final int _cursorIndexOfAuthorAvatar = CursorUtil.getColumnIndexOrThrow(_cursor, "authorAvatar");
          final int _cursorIndexOfReplyToUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "replyToUserId");
          final int _cursorIndexOfReplyToUserName = CursorUtil.getColumnIndexOrThrow(_cursor, "replyToUserName");
          final int _cursorIndexOfCreateTime = CursorUtil.getColumnIndexOrThrow(_cursor, "createTime");
          final int _cursorIndexOfLikeCount = CursorUtil.getColumnIndexOrThrow(_cursor, "likeCount");
          final int _cursorIndexOfFloor = CursorUtil.getColumnIndexOrThrow(_cursor, "floor");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isDeleted");
          final List<Reply> _result = new ArrayList<Reply>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Reply _item;
            _item = new Reply();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final long _tmpPostId;
            _tmpPostId = _cursor.getLong(_cursorIndexOfPostId);
            _item.setPostId(_tmpPostId);
            final long _tmpParentReplyId;
            _tmpParentReplyId = _cursor.getLong(_cursorIndexOfParentReplyId);
            _item.setParentReplyId(_tmpParentReplyId);
            final String _tmpContent;
            if (_cursor.isNull(_cursorIndexOfContent)) {
              _tmpContent = null;
            } else {
              _tmpContent = _cursor.getString(_cursorIndexOfContent);
            }
            _item.setContent(_tmpContent);
            final long _tmpAuthorId;
            _tmpAuthorId = _cursor.getLong(_cursorIndexOfAuthorId);
            _item.setAuthorId(_tmpAuthorId);
            final String _tmpAuthorName;
            if (_cursor.isNull(_cursorIndexOfAuthorName)) {
              _tmpAuthorName = null;
            } else {
              _tmpAuthorName = _cursor.getString(_cursorIndexOfAuthorName);
            }
            _item.setAuthorName(_tmpAuthorName);
            final String _tmpAuthorAvatar;
            if (_cursor.isNull(_cursorIndexOfAuthorAvatar)) {
              _tmpAuthorAvatar = null;
            } else {
              _tmpAuthorAvatar = _cursor.getString(_cursorIndexOfAuthorAvatar);
            }
            _item.setAuthorAvatar(_tmpAuthorAvatar);
            final long _tmpReplyToUserId;
            _tmpReplyToUserId = _cursor.getLong(_cursorIndexOfReplyToUserId);
            _item.setReplyToUserId(_tmpReplyToUserId);
            final String _tmpReplyToUserName;
            if (_cursor.isNull(_cursorIndexOfReplyToUserName)) {
              _tmpReplyToUserName = null;
            } else {
              _tmpReplyToUserName = _cursor.getString(_cursorIndexOfReplyToUserName);
            }
            _item.setReplyToUserName(_tmpReplyToUserName);
            final long _tmpCreateTime;
            _tmpCreateTime = _cursor.getLong(_cursorIndexOfCreateTime);
            _item.setCreateTime(_tmpCreateTime);
            final int _tmpLikeCount;
            _tmpLikeCount = _cursor.getInt(_cursorIndexOfLikeCount);
            _item.setLikeCount(_tmpLikeCount);
            final int _tmpFloor;
            _tmpFloor = _cursor.getInt(_cursorIndexOfFloor);
            _item.setFloor(_tmpFloor);
            final boolean _tmpIsDeleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp != 0;
            _item.setDeleted(_tmpIsDeleted);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Reply>> getSubRepliesByParentId(final long parentReplyId) {
    final String _sql = "SELECT * FROM replies WHERE parentReplyId = ? AND isDeleted = 0 ORDER BY createTime ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, parentReplyId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"replies"}, false, new Callable<List<Reply>>() {
      @Override
      @Nullable
      public List<Reply> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPostId = CursorUtil.getColumnIndexOrThrow(_cursor, "postId");
          final int _cursorIndexOfParentReplyId = CursorUtil.getColumnIndexOrThrow(_cursor, "parentReplyId");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfAuthorId = CursorUtil.getColumnIndexOrThrow(_cursor, "authorId");
          final int _cursorIndexOfAuthorName = CursorUtil.getColumnIndexOrThrow(_cursor, "authorName");
          final int _cursorIndexOfAuthorAvatar = CursorUtil.getColumnIndexOrThrow(_cursor, "authorAvatar");
          final int _cursorIndexOfReplyToUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "replyToUserId");
          final int _cursorIndexOfReplyToUserName = CursorUtil.getColumnIndexOrThrow(_cursor, "replyToUserName");
          final int _cursorIndexOfCreateTime = CursorUtil.getColumnIndexOrThrow(_cursor, "createTime");
          final int _cursorIndexOfLikeCount = CursorUtil.getColumnIndexOrThrow(_cursor, "likeCount");
          final int _cursorIndexOfFloor = CursorUtil.getColumnIndexOrThrow(_cursor, "floor");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isDeleted");
          final List<Reply> _result = new ArrayList<Reply>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Reply _item;
            _item = new Reply();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final long _tmpPostId;
            _tmpPostId = _cursor.getLong(_cursorIndexOfPostId);
            _item.setPostId(_tmpPostId);
            final long _tmpParentReplyId;
            _tmpParentReplyId = _cursor.getLong(_cursorIndexOfParentReplyId);
            _item.setParentReplyId(_tmpParentReplyId);
            final String _tmpContent;
            if (_cursor.isNull(_cursorIndexOfContent)) {
              _tmpContent = null;
            } else {
              _tmpContent = _cursor.getString(_cursorIndexOfContent);
            }
            _item.setContent(_tmpContent);
            final long _tmpAuthorId;
            _tmpAuthorId = _cursor.getLong(_cursorIndexOfAuthorId);
            _item.setAuthorId(_tmpAuthorId);
            final String _tmpAuthorName;
            if (_cursor.isNull(_cursorIndexOfAuthorName)) {
              _tmpAuthorName = null;
            } else {
              _tmpAuthorName = _cursor.getString(_cursorIndexOfAuthorName);
            }
            _item.setAuthorName(_tmpAuthorName);
            final String _tmpAuthorAvatar;
            if (_cursor.isNull(_cursorIndexOfAuthorAvatar)) {
              _tmpAuthorAvatar = null;
            } else {
              _tmpAuthorAvatar = _cursor.getString(_cursorIndexOfAuthorAvatar);
            }
            _item.setAuthorAvatar(_tmpAuthorAvatar);
            final long _tmpReplyToUserId;
            _tmpReplyToUserId = _cursor.getLong(_cursorIndexOfReplyToUserId);
            _item.setReplyToUserId(_tmpReplyToUserId);
            final String _tmpReplyToUserName;
            if (_cursor.isNull(_cursorIndexOfReplyToUserName)) {
              _tmpReplyToUserName = null;
            } else {
              _tmpReplyToUserName = _cursor.getString(_cursorIndexOfReplyToUserName);
            }
            _item.setReplyToUserName(_tmpReplyToUserName);
            final long _tmpCreateTime;
            _tmpCreateTime = _cursor.getLong(_cursorIndexOfCreateTime);
            _item.setCreateTime(_tmpCreateTime);
            final int _tmpLikeCount;
            _tmpLikeCount = _cursor.getInt(_cursorIndexOfLikeCount);
            _item.setLikeCount(_tmpLikeCount);
            final int _tmpFloor;
            _tmpFloor = _cursor.getInt(_cursorIndexOfFloor);
            _item.setFloor(_tmpFloor);
            final boolean _tmpIsDeleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp != 0;
            _item.setDeleted(_tmpIsDeleted);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Reply>> getRepliesByAuthor(final long authorId) {
    final String _sql = "SELECT * FROM replies WHERE authorId = ? AND isDeleted = 0 ORDER BY createTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, authorId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"replies"}, false, new Callable<List<Reply>>() {
      @Override
      @Nullable
      public List<Reply> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPostId = CursorUtil.getColumnIndexOrThrow(_cursor, "postId");
          final int _cursorIndexOfParentReplyId = CursorUtil.getColumnIndexOrThrow(_cursor, "parentReplyId");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfAuthorId = CursorUtil.getColumnIndexOrThrow(_cursor, "authorId");
          final int _cursorIndexOfAuthorName = CursorUtil.getColumnIndexOrThrow(_cursor, "authorName");
          final int _cursorIndexOfAuthorAvatar = CursorUtil.getColumnIndexOrThrow(_cursor, "authorAvatar");
          final int _cursorIndexOfReplyToUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "replyToUserId");
          final int _cursorIndexOfReplyToUserName = CursorUtil.getColumnIndexOrThrow(_cursor, "replyToUserName");
          final int _cursorIndexOfCreateTime = CursorUtil.getColumnIndexOrThrow(_cursor, "createTime");
          final int _cursorIndexOfLikeCount = CursorUtil.getColumnIndexOrThrow(_cursor, "likeCount");
          final int _cursorIndexOfFloor = CursorUtil.getColumnIndexOrThrow(_cursor, "floor");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isDeleted");
          final List<Reply> _result = new ArrayList<Reply>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Reply _item;
            _item = new Reply();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final long _tmpPostId;
            _tmpPostId = _cursor.getLong(_cursorIndexOfPostId);
            _item.setPostId(_tmpPostId);
            final long _tmpParentReplyId;
            _tmpParentReplyId = _cursor.getLong(_cursorIndexOfParentReplyId);
            _item.setParentReplyId(_tmpParentReplyId);
            final String _tmpContent;
            if (_cursor.isNull(_cursorIndexOfContent)) {
              _tmpContent = null;
            } else {
              _tmpContent = _cursor.getString(_cursorIndexOfContent);
            }
            _item.setContent(_tmpContent);
            final long _tmpAuthorId;
            _tmpAuthorId = _cursor.getLong(_cursorIndexOfAuthorId);
            _item.setAuthorId(_tmpAuthorId);
            final String _tmpAuthorName;
            if (_cursor.isNull(_cursorIndexOfAuthorName)) {
              _tmpAuthorName = null;
            } else {
              _tmpAuthorName = _cursor.getString(_cursorIndexOfAuthorName);
            }
            _item.setAuthorName(_tmpAuthorName);
            final String _tmpAuthorAvatar;
            if (_cursor.isNull(_cursorIndexOfAuthorAvatar)) {
              _tmpAuthorAvatar = null;
            } else {
              _tmpAuthorAvatar = _cursor.getString(_cursorIndexOfAuthorAvatar);
            }
            _item.setAuthorAvatar(_tmpAuthorAvatar);
            final long _tmpReplyToUserId;
            _tmpReplyToUserId = _cursor.getLong(_cursorIndexOfReplyToUserId);
            _item.setReplyToUserId(_tmpReplyToUserId);
            final String _tmpReplyToUserName;
            if (_cursor.isNull(_cursorIndexOfReplyToUserName)) {
              _tmpReplyToUserName = null;
            } else {
              _tmpReplyToUserName = _cursor.getString(_cursorIndexOfReplyToUserName);
            }
            _item.setReplyToUserName(_tmpReplyToUserName);
            final long _tmpCreateTime;
            _tmpCreateTime = _cursor.getLong(_cursorIndexOfCreateTime);
            _item.setCreateTime(_tmpCreateTime);
            final int _tmpLikeCount;
            _tmpLikeCount = _cursor.getInt(_cursorIndexOfLikeCount);
            _item.setLikeCount(_tmpLikeCount);
            final int _tmpFloor;
            _tmpFloor = _cursor.getInt(_cursorIndexOfFloor);
            _item.setFloor(_tmpFloor);
            final boolean _tmpIsDeleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp != 0;
            _item.setDeleted(_tmpIsDeleted);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public int getMaxFloor(final long postId) {
    final String _sql = "SELECT MAX(floor) FROM replies WHERE postId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, postId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int getReplyCountByPostId(final long postId) {
    final String _sql = "SELECT COUNT(*) FROM replies WHERE postId = ? AND isDeleted = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, postId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}

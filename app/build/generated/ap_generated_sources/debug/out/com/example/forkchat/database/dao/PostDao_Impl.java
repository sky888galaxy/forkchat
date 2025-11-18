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
import com.example.forkchat.model.Post;
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
public final class PostDao_Impl implements PostDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Post> __insertionAdapterOfPost;

  private final EntityDeletionOrUpdateAdapter<Post> __deletionAdapterOfPost;

  private final EntityDeletionOrUpdateAdapter<Post> __updateAdapterOfPost;

  private final SharedSQLiteStatement __preparedStmtOfIncrementViewCount;

  private final SharedSQLiteStatement __preparedStmtOfIncrementReplyCount;

  private final SharedSQLiteStatement __preparedStmtOfUpdateLikeCount;

  private final SharedSQLiteStatement __preparedStmtOfSoftDelete;

  public PostDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPost = new EntityInsertionAdapter<Post>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `posts` (`id`,`title`,`content`,`coverImage`,`authorId`,`authorName`,`authorAvatar`,`createTime`,`lastReplyTime`,`viewCount`,`replyCount`,`likeCount`,`board`,`tags`,`isDeleted`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Post entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitle());
        }
        if (entity.getContent() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getContent());
        }
        if (entity.getCoverImage() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getCoverImage());
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
        statement.bindLong(8, entity.getCreateTime());
        statement.bindLong(9, entity.getLastReplyTime());
        statement.bindLong(10, entity.getViewCount());
        statement.bindLong(11, entity.getReplyCount());
        statement.bindLong(12, entity.getLikeCount());
        if (entity.getBoard() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getBoard());
        }
        if (entity.getTags() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getTags());
        }
        final int _tmp = entity.isDeleted() ? 1 : 0;
        statement.bindLong(15, _tmp);
      }
    };
    this.__deletionAdapterOfPost = new EntityDeletionOrUpdateAdapter<Post>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `posts` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Post entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfPost = new EntityDeletionOrUpdateAdapter<Post>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `posts` SET `id` = ?,`title` = ?,`content` = ?,`coverImage` = ?,`authorId` = ?,`authorName` = ?,`authorAvatar` = ?,`createTime` = ?,`lastReplyTime` = ?,`viewCount` = ?,`replyCount` = ?,`likeCount` = ?,`board` = ?,`tags` = ?,`isDeleted` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Post entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitle());
        }
        if (entity.getContent() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getContent());
        }
        if (entity.getCoverImage() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getCoverImage());
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
        statement.bindLong(8, entity.getCreateTime());
        statement.bindLong(9, entity.getLastReplyTime());
        statement.bindLong(10, entity.getViewCount());
        statement.bindLong(11, entity.getReplyCount());
        statement.bindLong(12, entity.getLikeCount());
        if (entity.getBoard() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getBoard());
        }
        if (entity.getTags() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getTags());
        }
        final int _tmp = entity.isDeleted() ? 1 : 0;
        statement.bindLong(15, _tmp);
        statement.bindLong(16, entity.getId());
      }
    };
    this.__preparedStmtOfIncrementViewCount = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE posts SET viewCount = viewCount + 1 WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfIncrementReplyCount = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE posts SET replyCount = replyCount + 1, lastReplyTime = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateLikeCount = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE posts SET likeCount = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfSoftDelete = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE posts SET isDeleted = 1 WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public long insert(final Post post) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfPost.insertAndReturnId(post);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Post post) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfPost.handle(post);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Post post) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfPost.handle(post);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void incrementViewCount(final long postId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfIncrementViewCount.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, postId);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfIncrementViewCount.release(_stmt);
    }
  }

  @Override
  public void incrementReplyCount(final long postId, final long replyTime) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfIncrementReplyCount.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, replyTime);
    _argIndex = 2;
    _stmt.bindLong(_argIndex, postId);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfIncrementReplyCount.release(_stmt);
    }
  }

  @Override
  public void updateLikeCount(final long postId, final int likeCount) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateLikeCount.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, likeCount);
    _argIndex = 2;
    _stmt.bindLong(_argIndex, postId);
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
  public void softDelete(final long postId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfSoftDelete.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, postId);
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
  public LiveData<Post> getPostById(final long postId) {
    final String _sql = "SELECT * FROM posts WHERE id = ? AND isDeleted = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, postId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"posts"}, false, new Callable<Post>() {
      @Override
      @Nullable
      public Post call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfCoverImage = CursorUtil.getColumnIndexOrThrow(_cursor, "coverImage");
          final int _cursorIndexOfAuthorId = CursorUtil.getColumnIndexOrThrow(_cursor, "authorId");
          final int _cursorIndexOfAuthorName = CursorUtil.getColumnIndexOrThrow(_cursor, "authorName");
          final int _cursorIndexOfAuthorAvatar = CursorUtil.getColumnIndexOrThrow(_cursor, "authorAvatar");
          final int _cursorIndexOfCreateTime = CursorUtil.getColumnIndexOrThrow(_cursor, "createTime");
          final int _cursorIndexOfLastReplyTime = CursorUtil.getColumnIndexOrThrow(_cursor, "lastReplyTime");
          final int _cursorIndexOfViewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "viewCount");
          final int _cursorIndexOfReplyCount = CursorUtil.getColumnIndexOrThrow(_cursor, "replyCount");
          final int _cursorIndexOfLikeCount = CursorUtil.getColumnIndexOrThrow(_cursor, "likeCount");
          final int _cursorIndexOfBoard = CursorUtil.getColumnIndexOrThrow(_cursor, "board");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isDeleted");
          final Post _result;
          if (_cursor.moveToFirst()) {
            _result = new Post();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _result.setId(_tmpId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            _result.setTitle(_tmpTitle);
            final String _tmpContent;
            if (_cursor.isNull(_cursorIndexOfContent)) {
              _tmpContent = null;
            } else {
              _tmpContent = _cursor.getString(_cursorIndexOfContent);
            }
            _result.setContent(_tmpContent);
            final String _tmpCoverImage;
            if (_cursor.isNull(_cursorIndexOfCoverImage)) {
              _tmpCoverImage = null;
            } else {
              _tmpCoverImage = _cursor.getString(_cursorIndexOfCoverImage);
            }
            _result.setCoverImage(_tmpCoverImage);
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
            final long _tmpCreateTime;
            _tmpCreateTime = _cursor.getLong(_cursorIndexOfCreateTime);
            _result.setCreateTime(_tmpCreateTime);
            final long _tmpLastReplyTime;
            _tmpLastReplyTime = _cursor.getLong(_cursorIndexOfLastReplyTime);
            _result.setLastReplyTime(_tmpLastReplyTime);
            final int _tmpViewCount;
            _tmpViewCount = _cursor.getInt(_cursorIndexOfViewCount);
            _result.setViewCount(_tmpViewCount);
            final int _tmpReplyCount;
            _tmpReplyCount = _cursor.getInt(_cursorIndexOfReplyCount);
            _result.setReplyCount(_tmpReplyCount);
            final int _tmpLikeCount;
            _tmpLikeCount = _cursor.getInt(_cursorIndexOfLikeCount);
            _result.setLikeCount(_tmpLikeCount);
            final String _tmpBoard;
            if (_cursor.isNull(_cursorIndexOfBoard)) {
              _tmpBoard = null;
            } else {
              _tmpBoard = _cursor.getString(_cursorIndexOfBoard);
            }
            _result.setBoard(_tmpBoard);
            final String _tmpTags;
            if (_cursor.isNull(_cursorIndexOfTags)) {
              _tmpTags = null;
            } else {
              _tmpTags = _cursor.getString(_cursorIndexOfTags);
            }
            _result.setTags(_tmpTags);
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
  public LiveData<List<Post>> getAllPostsByCreateTime() {
    final String _sql = "SELECT * FROM posts WHERE isDeleted = 0 ORDER BY createTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"posts"}, false, new Callable<List<Post>>() {
      @Override
      @Nullable
      public List<Post> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfCoverImage = CursorUtil.getColumnIndexOrThrow(_cursor, "coverImage");
          final int _cursorIndexOfAuthorId = CursorUtil.getColumnIndexOrThrow(_cursor, "authorId");
          final int _cursorIndexOfAuthorName = CursorUtil.getColumnIndexOrThrow(_cursor, "authorName");
          final int _cursorIndexOfAuthorAvatar = CursorUtil.getColumnIndexOrThrow(_cursor, "authorAvatar");
          final int _cursorIndexOfCreateTime = CursorUtil.getColumnIndexOrThrow(_cursor, "createTime");
          final int _cursorIndexOfLastReplyTime = CursorUtil.getColumnIndexOrThrow(_cursor, "lastReplyTime");
          final int _cursorIndexOfViewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "viewCount");
          final int _cursorIndexOfReplyCount = CursorUtil.getColumnIndexOrThrow(_cursor, "replyCount");
          final int _cursorIndexOfLikeCount = CursorUtil.getColumnIndexOrThrow(_cursor, "likeCount");
          final int _cursorIndexOfBoard = CursorUtil.getColumnIndexOrThrow(_cursor, "board");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isDeleted");
          final List<Post> _result = new ArrayList<Post>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Post _item;
            _item = new Post();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            _item.setTitle(_tmpTitle);
            final String _tmpContent;
            if (_cursor.isNull(_cursorIndexOfContent)) {
              _tmpContent = null;
            } else {
              _tmpContent = _cursor.getString(_cursorIndexOfContent);
            }
            _item.setContent(_tmpContent);
            final String _tmpCoverImage;
            if (_cursor.isNull(_cursorIndexOfCoverImage)) {
              _tmpCoverImage = null;
            } else {
              _tmpCoverImage = _cursor.getString(_cursorIndexOfCoverImage);
            }
            _item.setCoverImage(_tmpCoverImage);
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
            final long _tmpCreateTime;
            _tmpCreateTime = _cursor.getLong(_cursorIndexOfCreateTime);
            _item.setCreateTime(_tmpCreateTime);
            final long _tmpLastReplyTime;
            _tmpLastReplyTime = _cursor.getLong(_cursorIndexOfLastReplyTime);
            _item.setLastReplyTime(_tmpLastReplyTime);
            final int _tmpViewCount;
            _tmpViewCount = _cursor.getInt(_cursorIndexOfViewCount);
            _item.setViewCount(_tmpViewCount);
            final int _tmpReplyCount;
            _tmpReplyCount = _cursor.getInt(_cursorIndexOfReplyCount);
            _item.setReplyCount(_tmpReplyCount);
            final int _tmpLikeCount;
            _tmpLikeCount = _cursor.getInt(_cursorIndexOfLikeCount);
            _item.setLikeCount(_tmpLikeCount);
            final String _tmpBoard;
            if (_cursor.isNull(_cursorIndexOfBoard)) {
              _tmpBoard = null;
            } else {
              _tmpBoard = _cursor.getString(_cursorIndexOfBoard);
            }
            _item.setBoard(_tmpBoard);
            final String _tmpTags;
            if (_cursor.isNull(_cursorIndexOfTags)) {
              _tmpTags = null;
            } else {
              _tmpTags = _cursor.getString(_cursorIndexOfTags);
            }
            _item.setTags(_tmpTags);
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
  public LiveData<List<Post>> getAllPostsByReplyTime() {
    final String _sql = "SELECT * FROM posts WHERE isDeleted = 0 ORDER BY lastReplyTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"posts"}, false, new Callable<List<Post>>() {
      @Override
      @Nullable
      public List<Post> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfCoverImage = CursorUtil.getColumnIndexOrThrow(_cursor, "coverImage");
          final int _cursorIndexOfAuthorId = CursorUtil.getColumnIndexOrThrow(_cursor, "authorId");
          final int _cursorIndexOfAuthorName = CursorUtil.getColumnIndexOrThrow(_cursor, "authorName");
          final int _cursorIndexOfAuthorAvatar = CursorUtil.getColumnIndexOrThrow(_cursor, "authorAvatar");
          final int _cursorIndexOfCreateTime = CursorUtil.getColumnIndexOrThrow(_cursor, "createTime");
          final int _cursorIndexOfLastReplyTime = CursorUtil.getColumnIndexOrThrow(_cursor, "lastReplyTime");
          final int _cursorIndexOfViewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "viewCount");
          final int _cursorIndexOfReplyCount = CursorUtil.getColumnIndexOrThrow(_cursor, "replyCount");
          final int _cursorIndexOfLikeCount = CursorUtil.getColumnIndexOrThrow(_cursor, "likeCount");
          final int _cursorIndexOfBoard = CursorUtil.getColumnIndexOrThrow(_cursor, "board");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isDeleted");
          final List<Post> _result = new ArrayList<Post>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Post _item;
            _item = new Post();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            _item.setTitle(_tmpTitle);
            final String _tmpContent;
            if (_cursor.isNull(_cursorIndexOfContent)) {
              _tmpContent = null;
            } else {
              _tmpContent = _cursor.getString(_cursorIndexOfContent);
            }
            _item.setContent(_tmpContent);
            final String _tmpCoverImage;
            if (_cursor.isNull(_cursorIndexOfCoverImage)) {
              _tmpCoverImage = null;
            } else {
              _tmpCoverImage = _cursor.getString(_cursorIndexOfCoverImage);
            }
            _item.setCoverImage(_tmpCoverImage);
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
            final long _tmpCreateTime;
            _tmpCreateTime = _cursor.getLong(_cursorIndexOfCreateTime);
            _item.setCreateTime(_tmpCreateTime);
            final long _tmpLastReplyTime;
            _tmpLastReplyTime = _cursor.getLong(_cursorIndexOfLastReplyTime);
            _item.setLastReplyTime(_tmpLastReplyTime);
            final int _tmpViewCount;
            _tmpViewCount = _cursor.getInt(_cursorIndexOfViewCount);
            _item.setViewCount(_tmpViewCount);
            final int _tmpReplyCount;
            _tmpReplyCount = _cursor.getInt(_cursorIndexOfReplyCount);
            _item.setReplyCount(_tmpReplyCount);
            final int _tmpLikeCount;
            _tmpLikeCount = _cursor.getInt(_cursorIndexOfLikeCount);
            _item.setLikeCount(_tmpLikeCount);
            final String _tmpBoard;
            if (_cursor.isNull(_cursorIndexOfBoard)) {
              _tmpBoard = null;
            } else {
              _tmpBoard = _cursor.getString(_cursorIndexOfBoard);
            }
            _item.setBoard(_tmpBoard);
            final String _tmpTags;
            if (_cursor.isNull(_cursorIndexOfTags)) {
              _tmpTags = null;
            } else {
              _tmpTags = _cursor.getString(_cursorIndexOfTags);
            }
            _item.setTags(_tmpTags);
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
  public LiveData<List<Post>> getPostsByBoard(final String board) {
    final String _sql = "SELECT * FROM posts WHERE board = ? AND isDeleted = 0 ORDER BY createTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (board == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, board);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"posts"}, false, new Callable<List<Post>>() {
      @Override
      @Nullable
      public List<Post> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfCoverImage = CursorUtil.getColumnIndexOrThrow(_cursor, "coverImage");
          final int _cursorIndexOfAuthorId = CursorUtil.getColumnIndexOrThrow(_cursor, "authorId");
          final int _cursorIndexOfAuthorName = CursorUtil.getColumnIndexOrThrow(_cursor, "authorName");
          final int _cursorIndexOfAuthorAvatar = CursorUtil.getColumnIndexOrThrow(_cursor, "authorAvatar");
          final int _cursorIndexOfCreateTime = CursorUtil.getColumnIndexOrThrow(_cursor, "createTime");
          final int _cursorIndexOfLastReplyTime = CursorUtil.getColumnIndexOrThrow(_cursor, "lastReplyTime");
          final int _cursorIndexOfViewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "viewCount");
          final int _cursorIndexOfReplyCount = CursorUtil.getColumnIndexOrThrow(_cursor, "replyCount");
          final int _cursorIndexOfLikeCount = CursorUtil.getColumnIndexOrThrow(_cursor, "likeCount");
          final int _cursorIndexOfBoard = CursorUtil.getColumnIndexOrThrow(_cursor, "board");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isDeleted");
          final List<Post> _result = new ArrayList<Post>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Post _item;
            _item = new Post();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            _item.setTitle(_tmpTitle);
            final String _tmpContent;
            if (_cursor.isNull(_cursorIndexOfContent)) {
              _tmpContent = null;
            } else {
              _tmpContent = _cursor.getString(_cursorIndexOfContent);
            }
            _item.setContent(_tmpContent);
            final String _tmpCoverImage;
            if (_cursor.isNull(_cursorIndexOfCoverImage)) {
              _tmpCoverImage = null;
            } else {
              _tmpCoverImage = _cursor.getString(_cursorIndexOfCoverImage);
            }
            _item.setCoverImage(_tmpCoverImage);
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
            final long _tmpCreateTime;
            _tmpCreateTime = _cursor.getLong(_cursorIndexOfCreateTime);
            _item.setCreateTime(_tmpCreateTime);
            final long _tmpLastReplyTime;
            _tmpLastReplyTime = _cursor.getLong(_cursorIndexOfLastReplyTime);
            _item.setLastReplyTime(_tmpLastReplyTime);
            final int _tmpViewCount;
            _tmpViewCount = _cursor.getInt(_cursorIndexOfViewCount);
            _item.setViewCount(_tmpViewCount);
            final int _tmpReplyCount;
            _tmpReplyCount = _cursor.getInt(_cursorIndexOfReplyCount);
            _item.setReplyCount(_tmpReplyCount);
            final int _tmpLikeCount;
            _tmpLikeCount = _cursor.getInt(_cursorIndexOfLikeCount);
            _item.setLikeCount(_tmpLikeCount);
            final String _tmpBoard;
            if (_cursor.isNull(_cursorIndexOfBoard)) {
              _tmpBoard = null;
            } else {
              _tmpBoard = _cursor.getString(_cursorIndexOfBoard);
            }
            _item.setBoard(_tmpBoard);
            final String _tmpTags;
            if (_cursor.isNull(_cursorIndexOfTags)) {
              _tmpTags = null;
            } else {
              _tmpTags = _cursor.getString(_cursorIndexOfTags);
            }
            _item.setTags(_tmpTags);
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
  public LiveData<List<Post>> getPostsByAuthor(final long authorId) {
    final String _sql = "SELECT * FROM posts WHERE authorId = ? AND isDeleted = 0 ORDER BY createTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, authorId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"posts"}, false, new Callable<List<Post>>() {
      @Override
      @Nullable
      public List<Post> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfCoverImage = CursorUtil.getColumnIndexOrThrow(_cursor, "coverImage");
          final int _cursorIndexOfAuthorId = CursorUtil.getColumnIndexOrThrow(_cursor, "authorId");
          final int _cursorIndexOfAuthorName = CursorUtil.getColumnIndexOrThrow(_cursor, "authorName");
          final int _cursorIndexOfAuthorAvatar = CursorUtil.getColumnIndexOrThrow(_cursor, "authorAvatar");
          final int _cursorIndexOfCreateTime = CursorUtil.getColumnIndexOrThrow(_cursor, "createTime");
          final int _cursorIndexOfLastReplyTime = CursorUtil.getColumnIndexOrThrow(_cursor, "lastReplyTime");
          final int _cursorIndexOfViewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "viewCount");
          final int _cursorIndexOfReplyCount = CursorUtil.getColumnIndexOrThrow(_cursor, "replyCount");
          final int _cursorIndexOfLikeCount = CursorUtil.getColumnIndexOrThrow(_cursor, "likeCount");
          final int _cursorIndexOfBoard = CursorUtil.getColumnIndexOrThrow(_cursor, "board");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isDeleted");
          final List<Post> _result = new ArrayList<Post>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Post _item;
            _item = new Post();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            _item.setTitle(_tmpTitle);
            final String _tmpContent;
            if (_cursor.isNull(_cursorIndexOfContent)) {
              _tmpContent = null;
            } else {
              _tmpContent = _cursor.getString(_cursorIndexOfContent);
            }
            _item.setContent(_tmpContent);
            final String _tmpCoverImage;
            if (_cursor.isNull(_cursorIndexOfCoverImage)) {
              _tmpCoverImage = null;
            } else {
              _tmpCoverImage = _cursor.getString(_cursorIndexOfCoverImage);
            }
            _item.setCoverImage(_tmpCoverImage);
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
            final long _tmpCreateTime;
            _tmpCreateTime = _cursor.getLong(_cursorIndexOfCreateTime);
            _item.setCreateTime(_tmpCreateTime);
            final long _tmpLastReplyTime;
            _tmpLastReplyTime = _cursor.getLong(_cursorIndexOfLastReplyTime);
            _item.setLastReplyTime(_tmpLastReplyTime);
            final int _tmpViewCount;
            _tmpViewCount = _cursor.getInt(_cursorIndexOfViewCount);
            _item.setViewCount(_tmpViewCount);
            final int _tmpReplyCount;
            _tmpReplyCount = _cursor.getInt(_cursorIndexOfReplyCount);
            _item.setReplyCount(_tmpReplyCount);
            final int _tmpLikeCount;
            _tmpLikeCount = _cursor.getInt(_cursorIndexOfLikeCount);
            _item.setLikeCount(_tmpLikeCount);
            final String _tmpBoard;
            if (_cursor.isNull(_cursorIndexOfBoard)) {
              _tmpBoard = null;
            } else {
              _tmpBoard = _cursor.getString(_cursorIndexOfBoard);
            }
            _item.setBoard(_tmpBoard);
            final String _tmpTags;
            if (_cursor.isNull(_cursorIndexOfTags)) {
              _tmpTags = null;
            } else {
              _tmpTags = _cursor.getString(_cursorIndexOfTags);
            }
            _item.setTags(_tmpTags);
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
  public LiveData<List<Post>> getPostsByTag(final String tag) {
    final String _sql = "SELECT * FROM posts WHERE tags LIKE ? AND isDeleted = 0 ORDER BY createTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (tag == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, tag);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"posts"}, false, new Callable<List<Post>>() {
      @Override
      @Nullable
      public List<Post> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfCoverImage = CursorUtil.getColumnIndexOrThrow(_cursor, "coverImage");
          final int _cursorIndexOfAuthorId = CursorUtil.getColumnIndexOrThrow(_cursor, "authorId");
          final int _cursorIndexOfAuthorName = CursorUtil.getColumnIndexOrThrow(_cursor, "authorName");
          final int _cursorIndexOfAuthorAvatar = CursorUtil.getColumnIndexOrThrow(_cursor, "authorAvatar");
          final int _cursorIndexOfCreateTime = CursorUtil.getColumnIndexOrThrow(_cursor, "createTime");
          final int _cursorIndexOfLastReplyTime = CursorUtil.getColumnIndexOrThrow(_cursor, "lastReplyTime");
          final int _cursorIndexOfViewCount = CursorUtil.getColumnIndexOrThrow(_cursor, "viewCount");
          final int _cursorIndexOfReplyCount = CursorUtil.getColumnIndexOrThrow(_cursor, "replyCount");
          final int _cursorIndexOfLikeCount = CursorUtil.getColumnIndexOrThrow(_cursor, "likeCount");
          final int _cursorIndexOfBoard = CursorUtil.getColumnIndexOrThrow(_cursor, "board");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isDeleted");
          final List<Post> _result = new ArrayList<Post>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Post _item;
            _item = new Post();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            _item.setTitle(_tmpTitle);
            final String _tmpContent;
            if (_cursor.isNull(_cursorIndexOfContent)) {
              _tmpContent = null;
            } else {
              _tmpContent = _cursor.getString(_cursorIndexOfContent);
            }
            _item.setContent(_tmpContent);
            final String _tmpCoverImage;
            if (_cursor.isNull(_cursorIndexOfCoverImage)) {
              _tmpCoverImage = null;
            } else {
              _tmpCoverImage = _cursor.getString(_cursorIndexOfCoverImage);
            }
            _item.setCoverImage(_tmpCoverImage);
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
            final long _tmpCreateTime;
            _tmpCreateTime = _cursor.getLong(_cursorIndexOfCreateTime);
            _item.setCreateTime(_tmpCreateTime);
            final long _tmpLastReplyTime;
            _tmpLastReplyTime = _cursor.getLong(_cursorIndexOfLastReplyTime);
            _item.setLastReplyTime(_tmpLastReplyTime);
            final int _tmpViewCount;
            _tmpViewCount = _cursor.getInt(_cursorIndexOfViewCount);
            _item.setViewCount(_tmpViewCount);
            final int _tmpReplyCount;
            _tmpReplyCount = _cursor.getInt(_cursorIndexOfReplyCount);
            _item.setReplyCount(_tmpReplyCount);
            final int _tmpLikeCount;
            _tmpLikeCount = _cursor.getInt(_cursorIndexOfLikeCount);
            _item.setLikeCount(_tmpLikeCount);
            final String _tmpBoard;
            if (_cursor.isNull(_cursorIndexOfBoard)) {
              _tmpBoard = null;
            } else {
              _tmpBoard = _cursor.getString(_cursorIndexOfBoard);
            }
            _item.setBoard(_tmpBoard);
            final String _tmpTags;
            if (_cursor.isNull(_cursorIndexOfTags)) {
              _tmpTags = null;
            } else {
              _tmpTags = _cursor.getString(_cursorIndexOfTags);
            }
            _item.setTags(_tmpTags);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}

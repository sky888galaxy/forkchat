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
import com.example.forkchat.model.Like;
import java.lang.Boolean;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class LikeDao_Impl implements LikeDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Like> __insertionAdapterOfLike;

  private final EntityDeletionOrUpdateAdapter<Like> __deletionAdapterOfLike;

  private final SharedSQLiteStatement __preparedStmtOfDeleteLike;

  public LikeDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfLike = new EntityInsertionAdapter<Like>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `likes` (`id`,`userId`,`targetId`,`targetType`,`createTime`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Like entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getUserId());
        statement.bindLong(3, entity.getTargetId());
        statement.bindLong(4, entity.getTargetType());
        statement.bindLong(5, entity.getCreateTime());
      }
    };
    this.__deletionAdapterOfLike = new EntityDeletionOrUpdateAdapter<Like>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `likes` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Like entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteLike = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM likes WHERE userId = ? AND targetId = ? AND targetType = ?";
        return _query;
      }
    };
  }

  @Override
  public long insert(final Like like) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfLike.insertAndReturnId(like);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Like like) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfLike.handle(like);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteLike(final long userId, final long targetId, final int targetType) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteLike.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, userId);
    _argIndex = 2;
    _stmt.bindLong(_argIndex, targetId);
    _argIndex = 3;
    _stmt.bindLong(_argIndex, targetType);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteLike.release(_stmt);
    }
  }

  @Override
  public Like getLike(final long userId, final long targetId, final int targetType) {
    final String _sql = "SELECT * FROM likes WHERE userId = ? AND targetId = ? AND targetType = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, targetId);
    _argIndex = 3;
    _statement.bindLong(_argIndex, targetType);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
      final int _cursorIndexOfTargetId = CursorUtil.getColumnIndexOrThrow(_cursor, "targetId");
      final int _cursorIndexOfTargetType = CursorUtil.getColumnIndexOrThrow(_cursor, "targetType");
      final int _cursorIndexOfCreateTime = CursorUtil.getColumnIndexOrThrow(_cursor, "createTime");
      final Like _result;
      if (_cursor.moveToFirst()) {
        _result = new Like();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _result.setId(_tmpId);
        final long _tmpUserId;
        _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
        _result.setUserId(_tmpUserId);
        final long _tmpTargetId;
        _tmpTargetId = _cursor.getLong(_cursorIndexOfTargetId);
        _result.setTargetId(_tmpTargetId);
        final int _tmpTargetType;
        _tmpTargetType = _cursor.getInt(_cursorIndexOfTargetType);
        _result.setTargetType(_tmpTargetType);
        final long _tmpCreateTime;
        _tmpCreateTime = _cursor.getLong(_cursorIndexOfCreateTime);
        _result.setCreateTime(_tmpCreateTime);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int getLikeCount(final long targetId, final int targetType) {
    final String _sql = "SELECT COUNT(*) FROM likes WHERE targetId = ? AND targetType = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, targetId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, targetType);
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
  public LiveData<Boolean> isLiked(final long userId, final long targetId, final int targetType) {
    final String _sql = "SELECT EXISTS(SELECT 1 FROM likes WHERE userId = ? AND targetId = ? AND targetType = ?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, targetId);
    _argIndex = 3;
    _statement.bindLong(_argIndex, targetType);
    return __db.getInvalidationTracker().createLiveData(new String[] {"likes"}, false, new Callable<Boolean>() {
      @Override
      @Nullable
      public Boolean call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Boolean _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp == null ? null : _tmp != 0;
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
  public LiveData<List<Like>> getLikesByUser(final long userId) {
    final String _sql = "SELECT * FROM likes WHERE userId = ? ORDER BY createTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"likes"}, false, new Callable<List<Like>>() {
      @Override
      @Nullable
      public List<Like> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfTargetId = CursorUtil.getColumnIndexOrThrow(_cursor, "targetId");
          final int _cursorIndexOfTargetType = CursorUtil.getColumnIndexOrThrow(_cursor, "targetType");
          final int _cursorIndexOfCreateTime = CursorUtil.getColumnIndexOrThrow(_cursor, "createTime");
          final List<Like> _result = new ArrayList<Like>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Like _item;
            _item = new Like();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final long _tmpUserId;
            _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
            _item.setUserId(_tmpUserId);
            final long _tmpTargetId;
            _tmpTargetId = _cursor.getLong(_cursorIndexOfTargetId);
            _item.setTargetId(_tmpTargetId);
            final int _tmpTargetType;
            _tmpTargetType = _cursor.getInt(_cursorIndexOfTargetType);
            _item.setTargetType(_tmpTargetType);
            final long _tmpCreateTime;
            _tmpCreateTime = _cursor.getLong(_cursorIndexOfCreateTime);
            _item.setCreateTime(_tmpCreateTime);
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

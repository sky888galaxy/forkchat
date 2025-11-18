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
import com.example.forkchat.model.Favorite;
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
public final class FavoriteDao_Impl implements FavoriteDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Favorite> __insertionAdapterOfFavorite;

  private final EntityDeletionOrUpdateAdapter<Favorite> __deletionAdapterOfFavorite;

  private final SharedSQLiteStatement __preparedStmtOfDeleteFavorite;

  public FavoriteDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFavorite = new EntityInsertionAdapter<Favorite>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `favorites` (`id`,`userId`,`postId`,`createTime`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Favorite entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getUserId());
        statement.bindLong(3, entity.getPostId());
        statement.bindLong(4, entity.getCreateTime());
      }
    };
    this.__deletionAdapterOfFavorite = new EntityDeletionOrUpdateAdapter<Favorite>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `favorites` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Favorite entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteFavorite = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM favorites WHERE userId = ? AND postId = ?";
        return _query;
      }
    };
  }

  @Override
  public long insert(final Favorite favorite) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfFavorite.insertAndReturnId(favorite);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Favorite favorite) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfFavorite.handle(favorite);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteFavorite(final long userId, final long postId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteFavorite.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, userId);
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
      __preparedStmtOfDeleteFavorite.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Favorite>> getFavoritesByUser(final long userId) {
    final String _sql = "SELECT * FROM favorites WHERE userId = ? ORDER BY createTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"favorites"}, false, new Callable<List<Favorite>>() {
      @Override
      @Nullable
      public List<Favorite> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfPostId = CursorUtil.getColumnIndexOrThrow(_cursor, "postId");
          final int _cursorIndexOfCreateTime = CursorUtil.getColumnIndexOrThrow(_cursor, "createTime");
          final List<Favorite> _result = new ArrayList<Favorite>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Favorite _item;
            _item = new Favorite();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final long _tmpUserId;
            _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
            _item.setUserId(_tmpUserId);
            final long _tmpPostId;
            _tmpPostId = _cursor.getLong(_cursorIndexOfPostId);
            _item.setPostId(_tmpPostId);
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

  @Override
  public Favorite getFavorite(final long userId, final long postId) {
    final String _sql = "SELECT * FROM favorites WHERE userId = ? AND postId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, postId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
      final int _cursorIndexOfPostId = CursorUtil.getColumnIndexOrThrow(_cursor, "postId");
      final int _cursorIndexOfCreateTime = CursorUtil.getColumnIndexOrThrow(_cursor, "createTime");
      final Favorite _result;
      if (_cursor.moveToFirst()) {
        _result = new Favorite();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _result.setId(_tmpId);
        final long _tmpUserId;
        _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
        _result.setUserId(_tmpUserId);
        final long _tmpPostId;
        _tmpPostId = _cursor.getLong(_cursorIndexOfPostId);
        _result.setPostId(_tmpPostId);
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
  public int getFavoriteCountByPostId(final long postId) {
    final String _sql = "SELECT COUNT(*) FROM favorites WHERE postId = ?";
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
  public LiveData<Boolean> isFavorited(final long userId, final long postId) {
    final String _sql = "SELECT EXISTS(SELECT 1 FROM favorites WHERE userId = ? AND postId = ?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, postId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"favorites"}, false, new Callable<Boolean>() {
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}

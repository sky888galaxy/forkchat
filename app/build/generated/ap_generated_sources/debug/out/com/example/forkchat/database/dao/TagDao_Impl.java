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
import com.example.forkchat.model.Tag;
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
public final class TagDao_Impl implements TagDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Tag> __insertionAdapterOfTag;

  private final EntityDeletionOrUpdateAdapter<Tag> __deletionAdapterOfTag;

  private final EntityDeletionOrUpdateAdapter<Tag> __updateAdapterOfTag;

  private final SharedSQLiteStatement __preparedStmtOfIncrementPostCount;

  public TagDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTag = new EntityInsertionAdapter<Tag>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `tags` (`id`,`name`,`color`,`postCount`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Tag entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getColor() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getColor());
        }
        statement.bindLong(4, entity.getPostCount());
      }
    };
    this.__deletionAdapterOfTag = new EntityDeletionOrUpdateAdapter<Tag>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `tags` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Tag entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfTag = new EntityDeletionOrUpdateAdapter<Tag>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `tags` SET `id` = ?,`name` = ?,`color` = ?,`postCount` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Tag entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getColor() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getColor());
        }
        statement.bindLong(4, entity.getPostCount());
        statement.bindLong(5, entity.getId());
      }
    };
    this.__preparedStmtOfIncrementPostCount = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE tags SET postCount = postCount + 1 WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public long insert(final Tag tag) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfTag.insertAndReturnId(tag);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Tag tag) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfTag.handle(tag);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Tag tag) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfTag.handle(tag);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void incrementPostCount(final long tagId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfIncrementPostCount.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, tagId);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfIncrementPostCount.release(_stmt);
    }
  }

  @Override
  public LiveData<Tag> getTagById(final long tagId) {
    final String _sql = "SELECT * FROM tags WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, tagId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"tags"}, false, new Callable<Tag>() {
      @Override
      @Nullable
      public Tag call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfPostCount = CursorUtil.getColumnIndexOrThrow(_cursor, "postCount");
          final Tag _result;
          if (_cursor.moveToFirst()) {
            _result = new Tag();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _result.setId(_tmpId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            _result.setName(_tmpName);
            final String _tmpColor;
            if (_cursor.isNull(_cursorIndexOfColor)) {
              _tmpColor = null;
            } else {
              _tmpColor = _cursor.getString(_cursorIndexOfColor);
            }
            _result.setColor(_tmpColor);
            final int _tmpPostCount;
            _tmpPostCount = _cursor.getInt(_cursorIndexOfPostCount);
            _result.setPostCount(_tmpPostCount);
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
  public Tag getTagByName(final String name) {
    final String _sql = "SELECT * FROM tags WHERE name = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (name == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, name);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
      final int _cursorIndexOfPostCount = CursorUtil.getColumnIndexOrThrow(_cursor, "postCount");
      final Tag _result;
      if (_cursor.moveToFirst()) {
        _result = new Tag();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _result.setId(_tmpId);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        _result.setName(_tmpName);
        final String _tmpColor;
        if (_cursor.isNull(_cursorIndexOfColor)) {
          _tmpColor = null;
        } else {
          _tmpColor = _cursor.getString(_cursorIndexOfColor);
        }
        _result.setColor(_tmpColor);
        final int _tmpPostCount;
        _tmpPostCount = _cursor.getInt(_cursorIndexOfPostCount);
        _result.setPostCount(_tmpPostCount);
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
  public LiveData<List<Tag>> getAllTagsByPopularity() {
    final String _sql = "SELECT * FROM tags ORDER BY postCount DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"tags"}, false, new Callable<List<Tag>>() {
      @Override
      @Nullable
      public List<Tag> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfPostCount = CursorUtil.getColumnIndexOrThrow(_cursor, "postCount");
          final List<Tag> _result = new ArrayList<Tag>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Tag _item;
            _item = new Tag();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            _item.setName(_tmpName);
            final String _tmpColor;
            if (_cursor.isNull(_cursorIndexOfColor)) {
              _tmpColor = null;
            } else {
              _tmpColor = _cursor.getString(_cursorIndexOfColor);
            }
            _item.setColor(_tmpColor);
            final int _tmpPostCount;
            _tmpPostCount = _cursor.getInt(_cursorIndexOfPostCount);
            _item.setPostCount(_tmpPostCount);
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
  public LiveData<List<Tag>> getAllTagsByName() {
    final String _sql = "SELECT * FROM tags ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"tags"}, false, new Callable<List<Tag>>() {
      @Override
      @Nullable
      public List<Tag> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfPostCount = CursorUtil.getColumnIndexOrThrow(_cursor, "postCount");
          final List<Tag> _result = new ArrayList<Tag>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Tag _item;
            _item = new Tag();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            _item.setName(_tmpName);
            final String _tmpColor;
            if (_cursor.isNull(_cursorIndexOfColor)) {
              _tmpColor = null;
            } else {
              _tmpColor = _cursor.getString(_cursorIndexOfColor);
            }
            _item.setColor(_tmpColor);
            final int _tmpPostCount;
            _tmpPostCount = _cursor.getInt(_cursorIndexOfPostCount);
            _item.setPostCount(_tmpPostCount);
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

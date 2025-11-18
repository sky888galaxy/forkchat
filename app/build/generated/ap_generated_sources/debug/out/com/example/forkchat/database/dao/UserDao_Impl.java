package com.example.forkchat.database.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.forkchat.model.User;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class UserDao_Impl implements UserDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<User> __insertionAdapterOfUser;

  private final EntityDeletionOrUpdateAdapter<User> __deletionAdapterOfUser;

  private final EntityDeletionOrUpdateAdapter<User> __updateAdapterOfUser;

  public UserDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUser = new EntityInsertionAdapter<User>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `users` (`id`,`username`,`avatar`,`email`,`createTime`,`registrationDate`,`bio`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final User entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindLong(1, entity.getId());
        }
        if (entity.getUsername() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getUsername());
        }
        if (entity.getAvatar() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getAvatar());
        }
        if (entity.getEmail() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getEmail());
        }
        if (entity.getCreateTime() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getCreateTime());
        }
        if (entity.getRegistrationDate() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getRegistrationDate());
        }
        if (entity.getBio() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getBio());
        }
      }
    };
    this.__deletionAdapterOfUser = new EntityDeletionOrUpdateAdapter<User>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `users` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final User entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindLong(1, entity.getId());
        }
      }
    };
    this.__updateAdapterOfUser = new EntityDeletionOrUpdateAdapter<User>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `users` SET `id` = ?,`username` = ?,`avatar` = ?,`email` = ?,`createTime` = ?,`registrationDate` = ?,`bio` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final User entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindLong(1, entity.getId());
        }
        if (entity.getUsername() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getUsername());
        }
        if (entity.getAvatar() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getAvatar());
        }
        if (entity.getEmail() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getEmail());
        }
        if (entity.getCreateTime() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getCreateTime());
        }
        if (entity.getRegistrationDate() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getRegistrationDate());
        }
        if (entity.getBio() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getBio());
        }
        if (entity.getId() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getId());
        }
      }
    };
  }

  @Override
  public long insert(final User user) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfUser.insertAndReturnId(user);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final User user) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfUser.handle(user);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final User user) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfUser.handle(user);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<User> getUserById(final long userId) {
    final String _sql = "SELECT * FROM users WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"users"}, false, new Callable<User>() {
      @Override
      @Nullable
      public User call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
          final int _cursorIndexOfAvatar = CursorUtil.getColumnIndexOrThrow(_cursor, "avatar");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfCreateTime = CursorUtil.getColumnIndexOrThrow(_cursor, "createTime");
          final int _cursorIndexOfRegistrationDate = CursorUtil.getColumnIndexOrThrow(_cursor, "registrationDate");
          final int _cursorIndexOfBio = CursorUtil.getColumnIndexOrThrow(_cursor, "bio");
          final User _result;
          if (_cursor.moveToFirst()) {
            _result = new User();
            final Long _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getLong(_cursorIndexOfId);
            }
            _result.setId(_tmpId);
            final String _tmpUsername;
            if (_cursor.isNull(_cursorIndexOfUsername)) {
              _tmpUsername = null;
            } else {
              _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            }
            _result.setUsername(_tmpUsername);
            final String _tmpAvatar;
            if (_cursor.isNull(_cursorIndexOfAvatar)) {
              _tmpAvatar = null;
            } else {
              _tmpAvatar = _cursor.getString(_cursorIndexOfAvatar);
            }
            _result.setAvatar(_tmpAvatar);
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            _result.setEmail(_tmpEmail);
            final String _tmpCreateTime;
            if (_cursor.isNull(_cursorIndexOfCreateTime)) {
              _tmpCreateTime = null;
            } else {
              _tmpCreateTime = _cursor.getString(_cursorIndexOfCreateTime);
            }
            _result.setCreateTime(_tmpCreateTime);
            final String _tmpRegistrationDate;
            if (_cursor.isNull(_cursorIndexOfRegistrationDate)) {
              _tmpRegistrationDate = null;
            } else {
              _tmpRegistrationDate = _cursor.getString(_cursorIndexOfRegistrationDate);
            }
            _result.setRegistrationDate(_tmpRegistrationDate);
            final String _tmpBio;
            if (_cursor.isNull(_cursorIndexOfBio)) {
              _tmpBio = null;
            } else {
              _tmpBio = _cursor.getString(_cursorIndexOfBio);
            }
            _result.setBio(_tmpBio);
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
  public User getUserByUsername(final String username) {
    final String _sql = "SELECT * FROM users WHERE username = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (username == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, username);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
      final int _cursorIndexOfAvatar = CursorUtil.getColumnIndexOrThrow(_cursor, "avatar");
      final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfCreateTime = CursorUtil.getColumnIndexOrThrow(_cursor, "createTime");
      final int _cursorIndexOfRegistrationDate = CursorUtil.getColumnIndexOrThrow(_cursor, "registrationDate");
      final int _cursorIndexOfBio = CursorUtil.getColumnIndexOrThrow(_cursor, "bio");
      final User _result;
      if (_cursor.moveToFirst()) {
        _result = new User();
        final Long _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getLong(_cursorIndexOfId);
        }
        _result.setId(_tmpId);
        final String _tmpUsername;
        if (_cursor.isNull(_cursorIndexOfUsername)) {
          _tmpUsername = null;
        } else {
          _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
        }
        _result.setUsername(_tmpUsername);
        final String _tmpAvatar;
        if (_cursor.isNull(_cursorIndexOfAvatar)) {
          _tmpAvatar = null;
        } else {
          _tmpAvatar = _cursor.getString(_cursorIndexOfAvatar);
        }
        _result.setAvatar(_tmpAvatar);
        final String _tmpEmail;
        if (_cursor.isNull(_cursorIndexOfEmail)) {
          _tmpEmail = null;
        } else {
          _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
        }
        _result.setEmail(_tmpEmail);
        final String _tmpCreateTime;
        if (_cursor.isNull(_cursorIndexOfCreateTime)) {
          _tmpCreateTime = null;
        } else {
          _tmpCreateTime = _cursor.getString(_cursorIndexOfCreateTime);
        }
        _result.setCreateTime(_tmpCreateTime);
        final String _tmpRegistrationDate;
        if (_cursor.isNull(_cursorIndexOfRegistrationDate)) {
          _tmpRegistrationDate = null;
        } else {
          _tmpRegistrationDate = _cursor.getString(_cursorIndexOfRegistrationDate);
        }
        _result.setRegistrationDate(_tmpRegistrationDate);
        final String _tmpBio;
        if (_cursor.isNull(_cursorIndexOfBio)) {
          _tmpBio = null;
        } else {
          _tmpBio = _cursor.getString(_cursorIndexOfBio);
        }
        _result.setBio(_tmpBio);
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
  public LiveData<List<User>> getAllUsers() {
    final String _sql = "SELECT * FROM users";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"users"}, false, new Callable<List<User>>() {
      @Override
      @Nullable
      public List<User> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
          final int _cursorIndexOfAvatar = CursorUtil.getColumnIndexOrThrow(_cursor, "avatar");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfCreateTime = CursorUtil.getColumnIndexOrThrow(_cursor, "createTime");
          final int _cursorIndexOfRegistrationDate = CursorUtil.getColumnIndexOrThrow(_cursor, "registrationDate");
          final int _cursorIndexOfBio = CursorUtil.getColumnIndexOrThrow(_cursor, "bio");
          final List<User> _result = new ArrayList<User>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final User _item;
            _item = new User();
            final Long _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getLong(_cursorIndexOfId);
            }
            _item.setId(_tmpId);
            final String _tmpUsername;
            if (_cursor.isNull(_cursorIndexOfUsername)) {
              _tmpUsername = null;
            } else {
              _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            }
            _item.setUsername(_tmpUsername);
            final String _tmpAvatar;
            if (_cursor.isNull(_cursorIndexOfAvatar)) {
              _tmpAvatar = null;
            } else {
              _tmpAvatar = _cursor.getString(_cursorIndexOfAvatar);
            }
            _item.setAvatar(_tmpAvatar);
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            _item.setEmail(_tmpEmail);
            final String _tmpCreateTime;
            if (_cursor.isNull(_cursorIndexOfCreateTime)) {
              _tmpCreateTime = null;
            } else {
              _tmpCreateTime = _cursor.getString(_cursorIndexOfCreateTime);
            }
            _item.setCreateTime(_tmpCreateTime);
            final String _tmpRegistrationDate;
            if (_cursor.isNull(_cursorIndexOfRegistrationDate)) {
              _tmpRegistrationDate = null;
            } else {
              _tmpRegistrationDate = _cursor.getString(_cursorIndexOfRegistrationDate);
            }
            _item.setRegistrationDate(_tmpRegistrationDate);
            final String _tmpBio;
            if (_cursor.isNull(_cursorIndexOfBio)) {
              _tmpBio = null;
            } else {
              _tmpBio = _cursor.getString(_cursorIndexOfBio);
            }
            _item.setBio(_tmpBio);
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

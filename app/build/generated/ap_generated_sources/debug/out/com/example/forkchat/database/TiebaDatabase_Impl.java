package com.example.forkchat.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.example.forkchat.database.dao.FavoriteDao;
import com.example.forkchat.database.dao.FavoriteDao_Impl;
import com.example.forkchat.database.dao.LikeDao;
import com.example.forkchat.database.dao.LikeDao_Impl;
import com.example.forkchat.database.dao.PostDao;
import com.example.forkchat.database.dao.PostDao_Impl;
import com.example.forkchat.database.dao.ReplyDao;
import com.example.forkchat.database.dao.ReplyDao_Impl;
import com.example.forkchat.database.dao.TagDao;
import com.example.forkchat.database.dao.TagDao_Impl;
import com.example.forkchat.database.dao.UserDao;
import com.example.forkchat.database.dao.UserDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class TiebaDatabase_Impl extends TiebaDatabase {
  private volatile UserDao _userDao;

  private volatile PostDao _postDao;

  private volatile ReplyDao _replyDao;

  private volatile TagDao _tagDao;

  private volatile FavoriteDao _favoriteDao;

  private volatile LikeDao _likeDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `users` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, `username` TEXT, `avatar` TEXT, `email` TEXT, `createTime` TEXT, `registrationDate` TEXT, `bio` TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `posts` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT, `content` TEXT, `coverImage` TEXT, `authorId` INTEGER NOT NULL, `authorName` TEXT, `authorAvatar` TEXT, `createTime` INTEGER NOT NULL, `lastReplyTime` INTEGER NOT NULL, `viewCount` INTEGER NOT NULL, `replyCount` INTEGER NOT NULL, `likeCount` INTEGER NOT NULL, `board` TEXT, `tags` TEXT, `isDeleted` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `replies` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `postId` INTEGER NOT NULL, `parentReplyId` INTEGER NOT NULL, `content` TEXT, `authorId` INTEGER NOT NULL, `authorName` TEXT, `authorAvatar` TEXT, `replyToUserId` INTEGER NOT NULL, `replyToUserName` TEXT, `createTime` INTEGER NOT NULL, `likeCount` INTEGER NOT NULL, `floor` INTEGER NOT NULL, `isDeleted` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `tags` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `color` TEXT, `postCount` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `favorites` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` INTEGER NOT NULL, `postId` INTEGER NOT NULL, `createTime` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `likes` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` INTEGER NOT NULL, `targetId` INTEGER NOT NULL, `targetType` INTEGER NOT NULL, `createTime` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '346934c40ba71fbd78d52f98840ac9e9')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `users`");
        db.execSQL("DROP TABLE IF EXISTS `posts`");
        db.execSQL("DROP TABLE IF EXISTS `replies`");
        db.execSQL("DROP TABLE IF EXISTS `tags`");
        db.execSQL("DROP TABLE IF EXISTS `favorites`");
        db.execSQL("DROP TABLE IF EXISTS `likes`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsUsers = new HashMap<String, TableInfo.Column>(7);
        _columnsUsers.put("id", new TableInfo.Column("id", "INTEGER", false, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("username", new TableInfo.Column("username", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("avatar", new TableInfo.Column("avatar", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("email", new TableInfo.Column("email", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("createTime", new TableInfo.Column("createTime", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("registrationDate", new TableInfo.Column("registrationDate", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("bio", new TableInfo.Column("bio", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUsers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUsers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUsers = new TableInfo("users", _columnsUsers, _foreignKeysUsers, _indicesUsers);
        final TableInfo _existingUsers = TableInfo.read(db, "users");
        if (!_infoUsers.equals(_existingUsers)) {
          return new RoomOpenHelper.ValidationResult(false, "users(com.example.forkchat.model.User).\n"
                  + " Expected:\n" + _infoUsers + "\n"
                  + " Found:\n" + _existingUsers);
        }
        final HashMap<String, TableInfo.Column> _columnsPosts = new HashMap<String, TableInfo.Column>(15);
        _columnsPosts.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPosts.put("title", new TableInfo.Column("title", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPosts.put("content", new TableInfo.Column("content", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPosts.put("coverImage", new TableInfo.Column("coverImage", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPosts.put("authorId", new TableInfo.Column("authorId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPosts.put("authorName", new TableInfo.Column("authorName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPosts.put("authorAvatar", new TableInfo.Column("authorAvatar", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPosts.put("createTime", new TableInfo.Column("createTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPosts.put("lastReplyTime", new TableInfo.Column("lastReplyTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPosts.put("viewCount", new TableInfo.Column("viewCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPosts.put("replyCount", new TableInfo.Column("replyCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPosts.put("likeCount", new TableInfo.Column("likeCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPosts.put("board", new TableInfo.Column("board", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPosts.put("tags", new TableInfo.Column("tags", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPosts.put("isDeleted", new TableInfo.Column("isDeleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPosts = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPosts = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPosts = new TableInfo("posts", _columnsPosts, _foreignKeysPosts, _indicesPosts);
        final TableInfo _existingPosts = TableInfo.read(db, "posts");
        if (!_infoPosts.equals(_existingPosts)) {
          return new RoomOpenHelper.ValidationResult(false, "posts(com.example.forkchat.model.Post).\n"
                  + " Expected:\n" + _infoPosts + "\n"
                  + " Found:\n" + _existingPosts);
        }
        final HashMap<String, TableInfo.Column> _columnsReplies = new HashMap<String, TableInfo.Column>(13);
        _columnsReplies.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReplies.put("postId", new TableInfo.Column("postId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReplies.put("parentReplyId", new TableInfo.Column("parentReplyId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReplies.put("content", new TableInfo.Column("content", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReplies.put("authorId", new TableInfo.Column("authorId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReplies.put("authorName", new TableInfo.Column("authorName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReplies.put("authorAvatar", new TableInfo.Column("authorAvatar", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReplies.put("replyToUserId", new TableInfo.Column("replyToUserId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReplies.put("replyToUserName", new TableInfo.Column("replyToUserName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReplies.put("createTime", new TableInfo.Column("createTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReplies.put("likeCount", new TableInfo.Column("likeCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReplies.put("floor", new TableInfo.Column("floor", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReplies.put("isDeleted", new TableInfo.Column("isDeleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysReplies = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesReplies = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoReplies = new TableInfo("replies", _columnsReplies, _foreignKeysReplies, _indicesReplies);
        final TableInfo _existingReplies = TableInfo.read(db, "replies");
        if (!_infoReplies.equals(_existingReplies)) {
          return new RoomOpenHelper.ValidationResult(false, "replies(com.example.forkchat.model.Reply).\n"
                  + " Expected:\n" + _infoReplies + "\n"
                  + " Found:\n" + _existingReplies);
        }
        final HashMap<String, TableInfo.Column> _columnsTags = new HashMap<String, TableInfo.Column>(4);
        _columnsTags.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTags.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTags.put("color", new TableInfo.Column("color", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTags.put("postCount", new TableInfo.Column("postCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTags = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTags = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTags = new TableInfo("tags", _columnsTags, _foreignKeysTags, _indicesTags);
        final TableInfo _existingTags = TableInfo.read(db, "tags");
        if (!_infoTags.equals(_existingTags)) {
          return new RoomOpenHelper.ValidationResult(false, "tags(com.example.forkchat.model.Tag).\n"
                  + " Expected:\n" + _infoTags + "\n"
                  + " Found:\n" + _existingTags);
        }
        final HashMap<String, TableInfo.Column> _columnsFavorites = new HashMap<String, TableInfo.Column>(4);
        _columnsFavorites.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavorites.put("userId", new TableInfo.Column("userId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavorites.put("postId", new TableInfo.Column("postId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavorites.put("createTime", new TableInfo.Column("createTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFavorites = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFavorites = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFavorites = new TableInfo("favorites", _columnsFavorites, _foreignKeysFavorites, _indicesFavorites);
        final TableInfo _existingFavorites = TableInfo.read(db, "favorites");
        if (!_infoFavorites.equals(_existingFavorites)) {
          return new RoomOpenHelper.ValidationResult(false, "favorites(com.example.forkchat.model.Favorite).\n"
                  + " Expected:\n" + _infoFavorites + "\n"
                  + " Found:\n" + _existingFavorites);
        }
        final HashMap<String, TableInfo.Column> _columnsLikes = new HashMap<String, TableInfo.Column>(5);
        _columnsLikes.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLikes.put("userId", new TableInfo.Column("userId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLikes.put("targetId", new TableInfo.Column("targetId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLikes.put("targetType", new TableInfo.Column("targetType", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLikes.put("createTime", new TableInfo.Column("createTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysLikes = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesLikes = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoLikes = new TableInfo("likes", _columnsLikes, _foreignKeysLikes, _indicesLikes);
        final TableInfo _existingLikes = TableInfo.read(db, "likes");
        if (!_infoLikes.equals(_existingLikes)) {
          return new RoomOpenHelper.ValidationResult(false, "likes(com.example.forkchat.model.Like).\n"
                  + " Expected:\n" + _infoLikes + "\n"
                  + " Found:\n" + _existingLikes);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "346934c40ba71fbd78d52f98840ac9e9", "ee52f9320e87822803b6bb6d4ed8e54e");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "users","posts","replies","tags","favorites","likes");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `users`");
      _db.execSQL("DELETE FROM `posts`");
      _db.execSQL("DELETE FROM `replies`");
      _db.execSQL("DELETE FROM `tags`");
      _db.execSQL("DELETE FROM `favorites`");
      _db.execSQL("DELETE FROM `likes`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(UserDao.class, UserDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PostDao.class, PostDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ReplyDao.class, ReplyDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TagDao.class, TagDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(FavoriteDao.class, FavoriteDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(LikeDao.class, LikeDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public UserDao userDao() {
    if (_userDao != null) {
      return _userDao;
    } else {
      synchronized(this) {
        if(_userDao == null) {
          _userDao = new UserDao_Impl(this);
        }
        return _userDao;
      }
    }
  }

  @Override
  public PostDao postDao() {
    if (_postDao != null) {
      return _postDao;
    } else {
      synchronized(this) {
        if(_postDao == null) {
          _postDao = new PostDao_Impl(this);
        }
        return _postDao;
      }
    }
  }

  @Override
  public ReplyDao replyDao() {
    if (_replyDao != null) {
      return _replyDao;
    } else {
      synchronized(this) {
        if(_replyDao == null) {
          _replyDao = new ReplyDao_Impl(this);
        }
        return _replyDao;
      }
    }
  }

  @Override
  public TagDao tagDao() {
    if (_tagDao != null) {
      return _tagDao;
    } else {
      synchronized(this) {
        if(_tagDao == null) {
          _tagDao = new TagDao_Impl(this);
        }
        return _tagDao;
      }
    }
  }

  @Override
  public FavoriteDao favoriteDao() {
    if (_favoriteDao != null) {
      return _favoriteDao;
    } else {
      synchronized(this) {
        if(_favoriteDao == null) {
          _favoriteDao = new FavoriteDao_Impl(this);
        }
        return _favoriteDao;
      }
    }
  }

  @Override
  public LikeDao likeDao() {
    if (_likeDao != null) {
      return _likeDao;
    } else {
      synchronized(this) {
        if(_likeDao == null) {
          _likeDao = new LikeDao_Impl(this);
        }
        return _likeDao;
      }
    }
  }
}

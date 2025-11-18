package com.example.forkchat.database;

import android.content.Context;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.forkchat.database.dao.FavoriteDao;
import com.example.forkchat.database.dao.LikeDao;
import com.example.forkchat.database.dao.PostDao;
import com.example.forkchat.database.dao.ReplyDao;
import com.example.forkchat.database.dao.TagDao;
import com.example.forkchat.database.dao.UserDao;
import com.example.forkchat.model.Favorite;
import com.example.forkchat.model.Like;
import com.example.forkchat.model.Post;
import com.example.forkchat.model.Reply;
import com.example.forkchat.model.Tag;
import com.example.forkchat.model.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@Database(entities = {User.class, Post.class, Reply.class, Tag.class, Favorite.class, Like.class},
        version = 1, exportSchema = false)
public abstract class TiebaDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract PostDao postDao();
    public abstract ReplyDao replyDao();
    public abstract TagDao tagDao();
    public abstract FavoriteDao favoriteDao();
    public abstract LikeDao likeDao();

    private static volatile TiebaDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static TiebaDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TiebaDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    TiebaDatabase.class, "tieba_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}


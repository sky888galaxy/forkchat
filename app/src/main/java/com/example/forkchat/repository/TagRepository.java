package com.example.forkchat.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.forkchat.database.TiebaDatabase;
import com.example.forkchat.database.dao.TagDao;
import com.example.forkchat.model.Tag;

import java.util.List;

/**
 * 标签仓库
 */
public class TagRepository {
    private TagDao tagDao;

    public TagRepository(Application application) {
        TiebaDatabase database = TiebaDatabase.getDatabase(application);
        tagDao = database.tagDao();
    }

    public LiveData<List<Tag>> getAllTagsByPopularity() {
        return tagDao.getAllTagsByPopularity();
    }

    public LiveData<List<Tag>> getAllTagsByName() {
        return tagDao.getAllTagsByName();
    }

    public void insertTag(Tag tag) {
        TiebaDatabase.databaseWriteExecutor.execute(() -> {
            tagDao.insert(tag);
        });
    }

    public void incrementPostCount(long tagId) {
        TiebaDatabase.databaseWriteExecutor.execute(() -> {
            tagDao.incrementPostCount(tagId);
        });
    }
}


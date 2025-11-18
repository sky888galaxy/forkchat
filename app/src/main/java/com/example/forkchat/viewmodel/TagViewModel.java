package com.example.forkchat.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.forkchat.model.Tag;
import com.example.forkchat.repository.TagRepository;

import java.util.List;

/**
 * 标签ViewModel
 */
public class TagViewModel extends AndroidViewModel {
    private TagRepository repository;
    private LiveData<List<Tag>> allTagsByPopularity;

    public TagViewModel(@NonNull Application application) {
        super(application);
        repository = new TagRepository(application);
        allTagsByPopularity = repository.getAllTagsByPopularity();
    }

    public LiveData<List<Tag>> getAllTagsByPopularity() {
        return allTagsByPopularity;
    }

    public LiveData<List<Tag>> getAllTagsByName() {
        return repository.getAllTagsByName();
    }

    public void insertTag(Tag tag) {
        repository.insertTag(tag);
    }

    public void incrementPostCount(long tagId) {
        repository.incrementPostCount(tagId);
    }
}


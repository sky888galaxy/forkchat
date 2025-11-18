package com.example.forkchat.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.forkchat.model.User;
import com.example.forkchat.model.UserProfile;
import com.example.forkchat.repository.UserRepository;

/**
 * 用户ViewModel
 */
public class UserViewModel extends AndroidViewModel {
    private UserRepository repository;
    private MutableLiveData<Long> currentUserId = new MutableLiveData<>(1L); // 默认用户ID为1
    private MutableLiveData<UserProfile> userProfile = new MutableLiveData<>();
    private MutableLiveData<User> currentUser = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> updateSuccess = new MutableLiveData<>();

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
        loadCurrentUser();
    }

    public LiveData<User> getUserById(long userId) {
        return repository.getUserById(userId);
    }

    public void insertUser(User user, UserRepository.OnUserInsertedListener listener) {
        repository.insertUser(user, listener);
    }

    public void updateUser(User user) {
        repository.updateUser(user);
    }

    public MutableLiveData<Long> getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(long userId) {
        currentUserId.setValue(userId);
    }

    public long getCurrentUserIdValue() {
        Long userId = currentUserId.getValue();
        return userId != null ? userId : 1L;
    }


    public LiveData<UserProfile> getUserProfile() {
        return userProfile;
    }

    public LiveData<User> getCurrentUser() {
        return currentUser;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Boolean> getUpdateSuccess() {
        return updateSuccess;
    }

    public void loadUserProfile(Long userId) {
        isLoading.setValue(true);
        errorMessage.setValue(null);

        new Thread(() -> {
            try {
                Thread.sleep(500); // 模拟网络延迟
                UserProfile profile = repository.getUserProfile(userId);
                userProfile.postValue(profile);
                isLoading.postValue(false);
            } catch (Exception e) {
                errorMessage.postValue("加载用户资料失败: " + e.getMessage());
                isLoading.postValue(false);
            }
        }).start();
    }

    public void loadCurrentUser() {
        currentUser.setValue(repository.getCurrentUser());
    }

    public void updateUserProfile(User user) {
        isLoading.setValue(true);
        errorMessage.setValue(null);

        new Thread(() -> {
            try {
                Thread.sleep(300);
                boolean success = repository.updateUserProfile(user);
                if (success) {
                    currentUser.postValue(user);
                    updateSuccess.postValue(true);
                    // 重新加载用户资料
                    if (user.getId() != null) {
                        loadUserProfile(user.getId());
                    }
                } else {
                    errorMessage.postValue("更新用户资料失败");
                }
                isLoading.postValue(false);
            } catch (Exception e) {
                errorMessage.postValue("更新用户资料失败: " + e.getMessage());
                isLoading.postValue(false);
            }
        }).start();
    }

    public void changePassword(String oldPassword, String newPassword) {
        isLoading.setValue(true);
        errorMessage.setValue(null);

        new Thread(() -> {
            try {
                Thread.sleep(300);
                boolean success = repository.changePassword(oldPassword, newPassword);
                if (success) {
                    updateSuccess.postValue(true);
                } else {
                    errorMessage.postValue("密码修改失败");
                }
                isLoading.postValue(false);
            } catch (Exception e) {
                errorMessage.postValue("密码修改失败: " + e.getMessage());
                isLoading.postValue(false);
            }
        }).start();
    }

    public void logout() {
        new Thread(() -> {
            repository.logout();
            currentUser.postValue(null);
        }).start();
    }

    public void clearUpdateStatus() {
        updateSuccess.setValue(false);
        errorMessage.setValue(null);
    }
}


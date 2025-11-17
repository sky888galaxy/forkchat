package com.example.forkchat.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.forkchat.model.User;
import com.example.forkchat.model.UserProfile;
import com.example.forkchat.repository.UserRepository;

public class UserViewModel extends ViewModel {

    private UserRepository userRepository = UserRepository.getInstance();

    private MutableLiveData<UserProfile> userProfile = new MutableLiveData<>();
    private MutableLiveData<User> currentUser = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> updateSuccess = new MutableLiveData<>();

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

    public UserViewModel() {
        loadCurrentUser();
    }

    public void loadUserProfile(String userId) {
        isLoading.setValue(true);
        errorMessage.setValue(null);

        new Thread(() -> {
            try {
                Thread.sleep(500); // 模拟网络延迟
                UserProfile profile = userRepository.getUserProfile(userId);
                userProfile.postValue(profile);
                isLoading.postValue(false);
            } catch (Exception e) {
                errorMessage.postValue("加载用户资料失败: " + e.getMessage());
                isLoading.postValue(false);
            }
        }).start();
    }

    public void loadCurrentUser() {
        currentUser.setValue(userRepository.getCurrentUser());
    }

    public void updateUserProfile(User user) {
        isLoading.setValue(true);
        errorMessage.setValue(null);

        new Thread(() -> {
            try {
                Thread.sleep(300);
                boolean success = userRepository.updateUserProfile(user);
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
                boolean success = userRepository.changePassword(oldPassword, newPassword);
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
            userRepository.logout();
            currentUser.postValue(null);
        }).start();
    }

    public void clearUpdateStatus() {
        updateSuccess.setValue(false);
        errorMessage.setValue(null);
    }
}
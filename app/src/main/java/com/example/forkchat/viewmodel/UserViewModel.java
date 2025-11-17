package com.example.forkchat.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.forkchat.model.User;
import com.example.forkchat.model.UserProfile;
import com.example.forkchat.repository.UserRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserViewModel extends ViewModel {

    private UserRepository userRepository;
    private ExecutorService executorService;

    private MutableLiveData<UserProfile> userProfile = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public UserViewModel() {
        this.userRepository = UserRepository.getInstance();
        this.executorService = Executors.newSingleThreadExecutor();
        isLoading.setValue(false);
    }

    public LiveData<UserProfile> getUserProfile() {
        return userProfile;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void loadUserProfile(String userId) {
        isLoading.setValue(true);
        errorMessage.setValue(null);

        executorService.execute(() -> {
            try {
                UserProfile profile = userRepository.getUserProfile(userId);
                userProfile.postValue(profile);
            } catch (Exception e) {
                errorMessage.postValue("加载用户信息失败: " + e.getMessage());
            } finally {
                isLoading.postValue(false);
            }
        });
    }

    public void updateProfile(User user) {
        isLoading.setValue(true);

        executorService.execute(() -> {
            try {
                boolean success = userRepository.updateUserProfile(user);
                if (success) {
                    // 更新本地数据
                    UserProfile currentProfile = userProfile.getValue();
                    if (currentProfile != null) {
                        currentProfile.setUser(user);
                        userProfile.postValue(currentProfile);
                    }
                }
            } catch (Exception e) {
                errorMessage.postValue("更新资料失败: " + e.getMessage());
            } finally {
                isLoading.postValue(false);
            }
        });
    }

    public void changePassword(String oldPassword, String newPassword) {
        isLoading.setValue(true);

        executorService.execute(() -> {
            try {
                boolean success = userRepository.changePassword(oldPassword, newPassword);
                if (!success) {
                    errorMessage.postValue("密码修改失败");
                }
            } catch (Exception e) {
                errorMessage.postValue("密码修改失败: " + e.getMessage());
            } finally {
                isLoading.postValue(false);
            }
        });
    }

    public void logout() {
        executorService.execute(() -> {
            try {
                userRepository.logout();
                // 这里可以添加清理登录状态等操作
            } catch (Exception e) {
                errorMessage.postValue("退出登录失败: " + e.getMessage());
            }
        });
    }

    public void clearError() {
        errorMessage.setValue(null);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }
}
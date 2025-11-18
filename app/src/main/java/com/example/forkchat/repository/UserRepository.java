package com.example.forkchat.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.forkchat.database.TiebaDatabase;
import com.example.forkchat.database.dao.UserDao;
import com.example.forkchat.model.Topic;
import com.example.forkchat.model.User;
import com.example.forkchat.model.UserProfile;

import java.util.Arrays;
import java.util.List;

/**
 * 用户仓库
 */
public class UserRepository {

    private UserDao userDao;
    private static UserRepository instance;
    private User currentUser;

    public UserRepository(Application application) {
        TiebaDatabase database = TiebaDatabase.getDatabase(application);
        userDao = database.userDao();
    }

    public LiveData<User> getUserById(long userId) {
        return userDao.getUserById(userId);
    }

    public void insertUser(User user, OnUserInsertedListener listener) {
        TiebaDatabase.databaseWriteExecutor.execute(() -> {
            long id = userDao.insert(user);
            if (listener != null) {
                listener.onUserInserted(id);
            }
        });
    }

    public void updateUser(User user) {
        TiebaDatabase.databaseWriteExecutor.execute(() -> {
            userDao.update(user);
        });
    }

    public LiveData<List<User>> getAllUsers() {
        return userDao.getAllUsers();
    }

    // 添加登录验证方法
    public void loginUser(String email, String password, OnLoginListener listener) {
        TiebaDatabase.databaseWriteExecutor.execute(() -> {
            User user = userDao.getUserByEmailAndPassword(email, password);
            if (listener != null) {
                if (user != null) {
                    this.currentUser = user;
                    listener.onLoginSuccess(user);
                } else {
                    listener.onLoginFailure("邮箱或密码错误");
                }
            }
        });
    }

    // 添加注册验证方法
    public void registerUser(String username, String email, String password, OnRegisterListener listener) {
        TiebaDatabase.databaseWriteExecutor.execute(() -> {
            // 检查用户名是否已存在
            User existingUser = userDao.getUserByUsername(username);
            if (existingUser != null) {
                if (listener != null) {
                    listener.onRegisterFailure("用户名已存在");
                }
                return;
            }

            // 检查邮箱是否已存在
            existingUser = userDao.getUserByEmail(email);
            if (existingUser != null) {
                if (listener != null) {
                    listener.onRegisterFailure("邮箱已存在");
                }
                return;
            }

            // 创建新用户
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setEmail(email);
            newUser.setPassword(password);
            newUser.setAvatar(""); // 默认头像
            newUser.setBio("这个人很懒，什么都没有写～");
            newUser.setCreateTime(String.valueOf(System.currentTimeMillis()));
            newUser.setRegistrationDate(String.valueOf(System.currentTimeMillis()));

            long userId = userDao.insert(newUser);
            if (listener != null) {
                if (userId > 0) {
                    listener.onRegisterSuccess(userId);
                } else {
                    listener.onRegisterFailure("注册失败");
                }
            }
        });
    }

    public interface OnUserInsertedListener {
        void onUserInserted(long userId);
    }

    public interface OnLoginListener {
        void onLoginSuccess(User user);
        void onLoginFailure(String error);
    }

    public interface OnRegisterListener {
        void onRegisterSuccess(long userId);
        void onRegisterFailure(String error);
    }

    public UserProfile getUserProfile(Long userId) {
        // 这里应该从数据库获取真实数据，暂时保留模拟数据
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 使用正确的 User 构造函数
        User user = new User();
        user.setId(userId);
        user.setUsername("白皓天");
        user.setEmail("baihaotian@example.com");
        user.setAvatar("https://example.com/avatar.jpg");
        user.setRegistrationDate("2024-01-15");
        user.setBio("热爱讨论的开发者");

        List<Topic> topics = Arrays.asList(
                new Topic(
                        "1",
                        "关于分叉式讨论的思考",
                        "分叉式讨论能够有效提升讨论效率...",
                        "1",
                        "白皓天",
                        Arrays.asList("科技", "讨论"),
                        "2024-03-20",
                        15,
                        8,
                        null
                ),
                new Topic(
                        "2",
                        "移动社交应用的发展趋势",
                        "当前移动社交应用在讨论体验上存在诸多痛点...",
                        "1",
                        "白皓天",
                        Arrays.asList("社交", "科技"),
                        "2024-03-18",
                        23,
                        12,
                        null
                )
        );

        return new UserProfile(user, topics, topics, topics.subList(0, 1));
    }

    public boolean updateUserProfile(User user) {
        try {
            Thread.sleep(300);
            this.currentUser = user;
            // 同时更新数据库
            updateUser(user);
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean changePassword(String oldPassword, String newPassword) {
        try {
            Thread.sleep(300);
            if (currentUser != null && currentUser.getPassword().equals(oldPassword)) {
                currentUser.setPassword(newPassword);
                updateUser(currentUser);
                return true;
            }
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean logout() {
        try {
            Thread.sleep(200);
            this.currentUser = null;
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    // 单例模式获取实例
    public static UserRepository getInstance(Application application) {
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (instance == null) {
                    instance = new UserRepository(application);
                }
            }
        }
        return instance;
    }
}
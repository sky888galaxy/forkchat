package com.example.forkchat.repository;

import com.example.forkchat.model.Topic;
import com.example.forkchat.model.User;
import com.example.forkchat.model.UserProfile;

import java.util.Arrays;
import java.util.List;

public class UserRepository {

    private static UserRepository instance;
    private User currentUser;

    private UserRepository() {
        // 私有构造函数
    }

    public static synchronized UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public UserProfile getUserProfile(String userId) {
        // 模拟网络请求延迟
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        User user = new User(
                "1",
                "白皓天",
                "baihaotian@example.com",
                "https://example.com/avatar.jpg",
                "2024-01-15",
                "热爱讨论的开发者"
        );

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
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean changePassword(String oldPassword, String newPassword) {
        try {
            Thread.sleep(300);
            // 模拟密码修改逻辑
            return true;
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
}
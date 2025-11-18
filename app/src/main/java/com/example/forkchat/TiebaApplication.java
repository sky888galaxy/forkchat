package com.example.forkchat;

import android.app.Application;

import com.example.forkchat.database.TiebaDatabase;
import com.example.forkchat.model.Post;
import com.example.forkchat.model.Tag;
import com.example.forkchat.model.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Application类 - 初始化应用
 */
public class TiebaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化数据库并插入示例数据
        initializeSampleData();
    }

    private void initializeSampleData() {
        TiebaDatabase.databaseWriteExecutor.execute(() -> {
            TiebaDatabase db = TiebaDatabase.getDatabase(this);

            // 简单的检查方式 - 尝试查询默认用户是否存在
            User existingUser = db.userDao().getUserByUsername("默认用户");

            if (existingUser == null) {
                // 创建默认用户
                User user = new User();
                user.setUsername("默认用户");
                user.setEmail("user@example.com");
                user.setPassword("123456"); // 默认密码
                user.setAvatar("");
                user.setBio("这是默认用户");

                String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                        .format(new Date());
                user.setCreateTime(currentTime);
                user.setRegistrationDate(currentTime);

                long userId = db.userDao().insert(user);

                // 创建示例标签
                String[] tagNames = {"科技", "生活", "学习", "娱乐", "讨论"};
                String[] tagColors = {"#FF5722", "#4CAF50", "#2196F3", "#FF9800", "#9C27B0"};

                for (int i = 0; i < tagNames.length; i++) {
                    Tag tag = new Tag(tagNames[i], tagColors[i]);
                    db.tagDao().insert(tag);
                }

                // 创建示例帖子
                String[] titles = {
                        "欢迎来到贴吧!",
                        "分享一些学习心得",
                        "今天天气真好",
                        "有什么好看的电影推荐吗?",
                        "讨论一下最新的技术趋势"
                };

                String[] contents = {
                        "这是一个全新的贴吧应用,支持发帖、回复、点赞等功能。欢迎大家积极发帖讨论！",
                        "最近在学习Android开发,感觉收获很多。Room数据库的使用确实很方便，Jetpack组件让开发效率大大提升。",
                        "阳光明媚,适合出去走走。大家有什么推荐的户外活动地点吗？",
                        "周末想看电影,求推荐！最近有什么值得一看的新片吗？",
                        "AI和机器学习越来越火了,大家怎么看这个发展趋势？对我们的生活会有哪些影响？"
                };

                String[] boards = {"热门", "学习", "水区", "娱乐", "讨论"};
                String[] tags = {"科技,学习", "学习", "生活", "娱乐", "科技,讨论"};

                long currentTimeMillis = System.currentTimeMillis();

                for (int i = 0; i < titles.length; i++) {
                    Post post = new Post();
                    post.setTitle(titles[i]);
                    post.setContent(contents[i]);
                    post.setAuthorId(userId);
                    post.setAuthorName("默认用户");
                    post.setBoard(boards[i]);
                    post.setTags(tags[i]);
                    post.setViewCount((i + 1) * 10);
                    post.setReplyCount((i + 1) * 3);
                    post.setLikeCount((i + 1) * 5);

                    // 设置时间戳（递减，让帖子有不同的时间）
                    post.setCreateTime(currentTimeMillis - i * 3600000L); // 每小时一个帖子
                    post.setLastReplyTime(currentTimeMillis - i * 1800000L); // 最后回复时间

                    // 设置封面图片（可选）
                    post.setCoverImage("");
                    post.setAuthorAvatar("");

                    db.postDao().insert(post);
                }

                // 创建一些热门帖子（高浏览量、高回复）
                Post hotPost = new Post();
                hotPost.setTitle("这个贴吧应用太棒了！强烈推荐！");
                hotPost.setContent("使用了一段时间，界面简洁，功能强大。分叉式讨论的设计很有意思，让对话更有条理。开发者用心了！");
                hotPost.setAuthorId(userId);
                hotPost.setAuthorName("默认用户");
                hotPost.setBoard("热门");
                hotPost.setTags("科技,推荐");
                hotPost.setViewCount(156);
                hotPost.setReplyCount(28);
                hotPost.setLikeCount(42);
                hotPost.setCreateTime(currentTimeMillis - 2 * 3600000L);
                hotPost.setLastReplyTime(currentTimeMillis - 300000L);
                db.postDao().insert(hotPost);

                System.out.println("示例数据初始化完成！");
            } else {
                System.out.println("数据库已有数据，跳过初始化");
            }
        });
    }
}
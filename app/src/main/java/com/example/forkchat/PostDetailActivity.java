package com.example.forkchat;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forkchat.adapter.ReplyAdapter;
import com.example.forkchat.database.TiebaDatabase;
import com.example.forkchat.database.dao.FavoriteDao;
import com.example.forkchat.database.dao.LikeDao;
import com.example.forkchat.model.Favorite;
import com.example.forkchat.model.Like;
import com.example.forkchat.model.Post;
import com.example.forkchat.model.Reply;
import com.example.forkchat.utils.ImageUtils;
import com.example.forkchat.utils.TimeUtils;
import com.example.forkchat.viewmodel.InteractionViewModel;
import com.example.forkchat.viewmodel.PostViewModel;
import com.example.forkchat.viewmodel.ReplyViewModel;
import com.example.forkchat.viewmodel.UserViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

/**
 * 帖子详情Activity
 */
public class PostDetailActivity extends AppCompatActivity {
    private PostViewModel postViewModel;
    private ReplyViewModel replyViewModel;
    private InteractionViewModel interactionViewModel;
    private UserViewModel userViewModel;

    private TextView tvTitle, tvAuthor, tvTime, tvContent;
    private ImageView ivAvatar, ivCover;
    private Button btnLike, btnFavorite, btnEdit, btnDelete;
    private RecyclerView recyclerViewReplies;
    private TextInputEditText etReply;
    private Button btnSendReply;
    private MaterialToolbar toolbar;

    private ReplyAdapter replyAdapter;
    private long postId;
    private long currentUserId;
    private Post currentPost;
    private Reply replyTarget; // 当前要回复的对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        postId = getIntent().getLongExtra("postId", -1);
        if (postId == -1) {
            finish();
            return;
        }

        initViews();
        initViewModels();
        setupToolbar();
        setupRecyclerView();
        loadPostDetail();
        setupListeners();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        tvTitle = findViewById(R.id.tvTitle);
        tvAuthor = findViewById(R.id.tvAuthor);
        tvTime = findViewById(R.id.tvTime);
        tvContent = findViewById(R.id.tvContent);
        ivAvatar = findViewById(R.id.ivAvatar);
        ivCover = findViewById(R.id.ivCover);
        btnLike = findViewById(R.id.btnLike);
        btnFavorite = findViewById(R.id.btnFavorite);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
        recyclerViewReplies = findViewById(R.id.recyclerViewReplies);
        etReply = findViewById(R.id.etReply);
        btnSendReply = findViewById(R.id.btnSendReply);
    }

    private void initViewModels() {
        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        replyViewModel = new ViewModelProvider(this).get(ReplyViewModel.class);
        interactionViewModel = new ViewModelProvider(this).get(InteractionViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        currentUserId = userViewModel.getCurrentUserIdValue();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void setupRecyclerView() {
        replyAdapter = new ReplyAdapter();
        recyclerViewReplies.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewReplies.setAdapter(replyAdapter);
       /* replyAdapter.setOnReplyActionListener(new ReplyAdapter.OnReplyActionListener() {
            @Override
            public void onLikeClick(Reply reply) {
                handleReplyLike(reply);
            }

            @Override
            public void onReplyClick(Reply reply) {
                replyTarget = reply;
                etReply.setHint("回复 @" + reply.getAuthorName());
                etReply.requestFocus();
            }
        });*/
    }

    private void loadPostDetail() {
        postViewModel.getPostById(postId).observe(this, post -> {
            if (post != null) {
                currentPost = post;
                displayPost(post);
                
                // 增加浏览数
                postViewModel.incrementViewCount(postId);
                
                // 检查是否是作者
                if (post.getAuthorId() == currentUserId) {
                    btnEdit.setVisibility(View.VISIBLE);
                    btnDelete.setVisibility(View.VISIBLE);
                }
            }
        });

        // 加载回复
        replyViewModel.getRepliesByPostId(postId).observe(this, replies -> {
          /*  if (replies != null) {
                replyAdapter.setReplyList(replies);
            }*/
        });

        // 检查点赞和收藏状态
        TiebaDatabase.databaseWriteExecutor.execute(() -> {
            LikeDao likeDao = TiebaDatabase.getDatabase(this).likeDao();
            FavoriteDao favoriteDao = TiebaDatabase.getDatabase(this).favoriteDao();
            
            Like existingLike = likeDao.getLike(currentUserId, postId, Like.TYPE_POST);
            Favorite existingFavorite = favoriteDao.getFavorite(currentUserId, postId);
            
            runOnUiThread(() -> {
                if (existingLike != null) {
                    btnLike.setText("已点赞");
                } else {
                    btnLike.setText("点赞");
                }
                
                if (existingFavorite != null) {
                    btnFavorite.setText("已收藏");
                } else {
                    btnFavorite.setText("收藏");
                }
            });
        });
    }

    private void displayPost(Post post) {
        tvTitle.setText(post.getTitle());
        tvAuthor.setText(post.getAuthorName());
        tvTime.setText(TimeUtils.getTimeAgo(post.getCreateTime()));
        tvContent.setText(post.getContent());

        if (post.getAuthorAvatar() != null && !post.getAuthorAvatar().isEmpty()) {
            ImageUtils.loadCircleImage(this, post.getAuthorAvatar(), ivAvatar);
        }

        if (post.getCoverImage() != null && !post.getCoverImage().isEmpty()) {
            ivCover.setVisibility(View.VISIBLE);
            ImageUtils.loadImage(this, post.getCoverImage(), ivCover);
        } else {
            ivCover.setVisibility(View.GONE);
        }
    }

    private void setupListeners() {
        // 点赞按钮
        btnLike.setOnClickListener(v -> handlePostLike());

        // 收藏按钮
        btnFavorite.setOnClickListener(v -> handleFavorite());

        // 编辑按钮
        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditPostActivity.class);
            intent.putExtra("postId", postId);
            startActivity(intent);
        });

        // 删除按钮
        btnDelete.setOnClickListener(v -> showDeleteConfirmDialog());

        // 发送回复按钮
        btnSendReply.setOnClickListener(v -> sendReply());
    }

    private void handlePostLike() {
        // 先获取当前点赞状态，然后切换
        TiebaDatabase.databaseWriteExecutor.execute(() -> {
            LikeDao likeDao = TiebaDatabase.getDatabase(this).likeDao();
            Like existingLike = likeDao.getLike(currentUserId, postId, Like.TYPE_POST);
            
            if (existingLike != null) {
                // 已点赞，取消点赞
                likeDao.deleteLike(currentUserId, postId, Like.TYPE_POST);
                int newCount = likeDao.getLikeCount(postId, Like.TYPE_POST);
                
                runOnUiThread(() -> {
                    postViewModel.updateLikeCount(postId, newCount);
                    btnLike.setText("点赞");
                    Toast.makeText(this, "已取消点赞", Toast.LENGTH_SHORT).show();
                });
            } else {
                // 未点赞，添加点赞
                Like like = new Like(currentUserId, postId, Like.TYPE_POST);
                likeDao.insert(like);
                int newCount = likeDao.getLikeCount(postId, Like.TYPE_POST);
                
                runOnUiThread(() -> {
                    postViewModel.updateLikeCount(postId, newCount);
                    btnLike.setText("已点赞");
                    Toast.makeText(this, "点赞成功", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private void handleReplyLike(Reply reply) {
        TiebaDatabase.databaseWriteExecutor.execute(() -> {
            LikeDao likeDao = TiebaDatabase.getDatabase(this).likeDao();
            Like existingLike = likeDao.getLike(currentUserId, reply.getId(), Like.TYPE_REPLY);
            
            if (existingLike != null) {
                // 已点赞，取消点赞
                likeDao.deleteLike(currentUserId, reply.getId(), Like.TYPE_REPLY);
                int newCount = likeDao.getLikeCount(reply.getId(), Like.TYPE_REPLY);
                
                runOnUiThread(() -> {
                    replyViewModel.updateLikeCount(reply.getId(), newCount);
                    Toast.makeText(this, "已取消点赞", Toast.LENGTH_SHORT).show();
                });
            } else {
                // 未点赞，添加点赞
                Like like = new Like(currentUserId, reply.getId(), Like.TYPE_REPLY);
                likeDao.insert(like);
                int newCount = likeDao.getLikeCount(reply.getId(), Like.TYPE_REPLY);
                
                runOnUiThread(() -> {
                    replyViewModel.updateLikeCount(reply.getId(), newCount);
                    Toast.makeText(this, "点赞成功", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private void handleFavorite() {
        TiebaDatabase.databaseWriteExecutor.execute(() -> {
            FavoriteDao favoriteDao = TiebaDatabase.getDatabase(this).favoriteDao();
            Favorite existingFavorite = favoriteDao.getFavorite(currentUserId, postId);
            
            if (existingFavorite != null) {
                // 已收藏，取消收藏
                favoriteDao.deleteFavorite(currentUserId, postId);
                runOnUiThread(() -> {
                    btnFavorite.setText("收藏");
                    Toast.makeText(this, "已取消收藏", Toast.LENGTH_SHORT).show();
                });
            } else {
                // 未收藏，添加收藏
                Favorite favorite = new Favorite(currentUserId, postId);
                favoriteDao.insert(favorite);
                runOnUiThread(() -> {
                    btnFavorite.setText("已收藏");
                    Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private void sendReply() {
        String content = etReply.getText() != null ? etReply.getText().toString().trim() : "";
        if (content.isEmpty()) {
            Toast.makeText(this, "请输入回复内容", Toast.LENGTH_SHORT).show();
            return;
        }

        Reply reply = new Reply(postId, content, currentUserId, "当前用户");
        
        if (replyTarget != null) {
            // 楼中楼回复
            if (replyTarget.getParentReplyId() == 0) {
                reply.setParentReplyId(replyTarget.getId());
            } else {
                reply.setParentReplyId(replyTarget.getParentReplyId());
            }
            reply.setReplyToUserId(replyTarget.getAuthorId());
            reply.setReplyToUserName(replyTarget.getAuthorName());
        }

        replyViewModel.insertReply(reply, replyId -> {
            runOnUiThread(() -> {
                postViewModel.incrementReplyCount(postId);
                etReply.setText("");
                etReply.setHint("发表回复...");
                replyTarget = null;
                Toast.makeText(this, "回复成功", Toast.LENGTH_SHORT).show();
            });
        });
    }

    private void showDeleteConfirmDialog() {
        new AlertDialog.Builder(this)
                .setTitle("确认删除")
                .setMessage("确定要删除这个帖子吗？")
                .setPositiveButton("删除", (dialog, which) -> {
                    postViewModel.deletePost(postId);
                    Toast.makeText(this, "帖子已删除", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("取消", null)
                .show();
    }
}


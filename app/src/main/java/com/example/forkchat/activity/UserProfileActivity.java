package com.example.forkchat.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forkchat.R;
import com.example.forkchat.adapter.TopicAdapter;
import com.example.forkchat.model.Topic;
import com.example.forkchat.model.UserProfile;
import com.example.forkchat.viewmodel.UserViewModel;
import com.google.android.material.tabs.TabLayout;

public class UserProfileActivity extends AppCompatActivity {

    private UserViewModel userViewModel;
    private TopicAdapter topicAdapter;

    private ImageView avatarImageView;
    private TextView usernameTextView;
    private TextView emailTextView;
    private TextView registrationDateTextView;
    private TextView bioTextView;
    private Button editProfileButton;
    private Button changePasswordButton;
    private Button logoutButton;
    private TabLayout tabLayout;
    private RecyclerView topicsRecyclerView;
    private ProgressBar progressBar;
    private TextView errorMessageTextView;

    private static final int REQUEST_EDIT_PROFILE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        initViews();
        initViewModel();
        setupRecyclerView();
        setupTabLayout();
        setupClickListeners();
        observeViewModel();

        // 加载用户数据
        userViewModel.loadUserProfile("current_user_id");
    }

    private void initViews() {
        avatarImageView = findViewById(R.id.iv_avatar);
        usernameTextView = findViewById(R.id.tv_username);
        emailTextView = findViewById(R.id.tv_email);
        registrationDateTextView = findViewById(R.id.tv_registration_date);
        bioTextView = findViewById(R.id.tv_bio);
        editProfileButton = findViewById(R.id.btn_edit_profile);
        changePasswordButton = findViewById(R.id.btn_change_password);
        logoutButton = findViewById(R.id.btn_logout);
        tabLayout = findViewById(R.id.tab_layout);
        topicsRecyclerView = findViewById(R.id.recycler_view_topics);
        progressBar = findViewById(R.id.progress_bar);
        errorMessageTextView = findViewById(R.id.tv_error_message);
    }

    private void initViewModel() {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    private void setupRecyclerView() {
        topicAdapter = new TopicAdapter(null, new TopicAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Topic topic) {
                // 处理话题点击事件
                Toast.makeText(UserProfileActivity.this, "点击话题: " + topic.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        topicsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        topicsRecyclerView.setAdapter(topicAdapter);
    }

    private void setupTabLayout() {
        // 添加选项卡
        tabLayout.addTab(tabLayout.newTab().setText("我的话题"));
        tabLayout.addTab(tabLayout.newTab().setText("参与讨论"));
        tabLayout.addTab(tabLayout.newTab().setText("收藏话题"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                updateTopicList(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // 什么都不做
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // 什么都不做
            }
        });
    }

    private void setupClickListeners() {
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到编辑资料页面
                Intent intent = new Intent(UserProfileActivity.this, EditProfileActivity.class);
                startActivityForResult(intent, REQUEST_EDIT_PROFILE);
            }
        });

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到修改密码页面
                Intent intent = new Intent(UserProfileActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutDialog();
            }
        });
    }

    private void observeViewModel() {
        userViewModel.getUserProfile().observe(this, new Observer<UserProfile>() {
            @Override
            public void onChanged(UserProfile userProfile) {
                if (userProfile != null) {
                    updateUserInfo(userProfile);
                    updateTopicList(tabLayout.getSelectedTabPosition());
                }
            }
        });

        userViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                topicsRecyclerView.setVisibility(isLoading ? View.GONE : View.VISIBLE);
            }
        });

        userViewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                if (errorMessage != null) {
                    errorMessageTextView.setText(errorMessage);
                    errorMessageTextView.setVisibility(View.VISIBLE);
                    Toast.makeText(UserProfileActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                } else {
                    errorMessageTextView.setVisibility(View.GONE);
                }
            }
        });
    }

    private void updateUserInfo(UserProfile userProfile) {
        usernameTextView.setText(userProfile.getUser().getUsername());
        emailTextView.setText(userProfile.getUser().getEmail());
        registrationDateTextView.setText("注册时间: " + userProfile.getUser().getRegistrationDate());
        bioTextView.setText(userProfile.getUser().getBio());
        // 这里可以添加加载头像的逻辑
        // 例如使用 Glide 或 Picasso 加载头像
    }

    private void updateTopicList(int tabPosition) {
        UserProfile userProfile = userViewModel.getUserProfile().getValue();
        if (userProfile == null) return;

        switch (tabPosition) {
            case 0: // 我的话题
                topicAdapter.updateTopics(userProfile.getTopics());
                break;
            case 1: // 参与讨论
                topicAdapter.updateTopics(userProfile.getParticipatedTopics());
                break;
            case 2: // 收藏话题
                topicAdapter.updateTopics(userProfile.getCollectedTopics());
                break;
        }
    }

    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("确认退出")
                .setMessage("确定要退出登录吗？")
                .setPositiveButton("确定", (dialog, which) -> {
                    userViewModel.logout();
                    finish();
                    Toast.makeText(UserProfileActivity.this, "已退出登录", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("取消", null)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EDIT_PROFILE && resultCode == Activity.RESULT_OK) {
            // 重新加载用户数据
            userViewModel.loadUserProfile("current_user_id");
            Toast.makeText(this, "资料更新成功", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 确保数据是最新的
        userViewModel.loadUserProfile("current_user_id");
    }
}
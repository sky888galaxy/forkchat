package com.example.forkchat.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
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
    private TabLayout tabLayout;
    private RecyclerView topicsRecyclerView;
    private ProgressBar progressBar;
    private TextView errorMessageTextView;

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
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void setupClickListeners() {
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到编辑资料页面
                Toast.makeText(UserProfileActivity.this, "跳转到编辑资料", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void observeViewModel() {
        userViewModel.getUserProfile().observe(this, userProfile -> {
            if (userProfile != null) {
                updateUserInfo(userProfile);
                updateTopicList(tabLayout.getSelectedTabPosition());
            }
        });

        userViewModel.getIsLoading().observe(this, isLoading -> {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            topicsRecyclerView.setVisibility(isLoading ? View.GONE : View.VISIBLE);
        });

        userViewModel.getErrorMessage().observe(this, errorMessage -> {
            if (errorMessage != null) {
                errorMessageTextView.setText(errorMessage);
                errorMessageTextView.setVisibility(View.VISIBLE);
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
            } else {
                errorMessageTextView.setVisibility(View.GONE);
            }
        });
    }

    private void updateUserInfo(UserProfile userProfile) {
        usernameTextView.setText(userProfile.getUser().getUsername());
        emailTextView.setText(userProfile.getUser().getEmail());
        registrationDateTextView.setText("注册时间: " + userProfile.getUser().getRegistrationDate());
        bioTextView.setText(userProfile.getUser().getBio());
        // 这里可以添加加载头像的逻辑
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
}
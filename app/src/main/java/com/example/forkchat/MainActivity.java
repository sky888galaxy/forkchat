package com.example.forkchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forkchat.activity.UserProfileActivity;
import com.example.forkchat.adapter.PostAdapter;
import com.example.forkchat.model.Tag;
import com.example.forkchat.viewmodel.PostViewModel;
import com.example.forkchat.viewmodel.TagViewModel;
import com.example.forkchat.viewmodel.UserViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.Arrays;

/**
 * 主Activity - 显示帖子列表
 */
public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PostAdapter adapter;
    private PostViewModel postViewModel;
    private TagViewModel tagViewModel;
    private UserViewModel userViewModel;
    private TabLayout tabLayout;
    private FloatingActionButton fabAddPost;

    private String currentBoard = "全部";
    private String currentTag = null;
    private String currentSort = "create_time";
    private View userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.activity_main);

            initViews();
            initViewModels();
            setupRecyclerView();
            setupTabs();
            setupListeners();
            loadPosts();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "初始化错误: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        tabLayout = findViewById(R.id.tabLayout);
        fabAddPost = findViewById(R.id.fabAddPost);
        userInfo = findViewById(R.id.userInfo);
        userInfo.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
            startActivity(intent);
        });
    }

    private void initViewModels() {
        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        tagViewModel = new ViewModelProvider(this).get(TagViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    private void setupRecyclerView() {
        adapter = new PostAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnPostClickListener(post -> {
            Intent intent = new Intent(MainActivity.this, com.example.forkchat.activity.MainActivity.class);
            intent.putExtra("postId", post.getId());
            startActivity(intent);
        });
    }

    private void setupTabs() {
        // 添加版块标签
        tabLayout.addTab(tabLayout.newTab().setText("全部"));
        tabLayout.addTab(tabLayout.newTab().setText("热门"));
        tabLayout.addTab(tabLayout.newTab().setText("最新"));
        tabLayout.addTab(tabLayout.newTab().setText("水区"));

        // 观察标签并添加到TabLayout
        tagViewModel.getAllTagsByPopularity().observe(this, tags -> {
            if (tags != null && !tags.isEmpty()) {
                for (Tag tag : tags) {
                    tabLayout.addTab(tabLayout.newTab().setText(tag.getName()));
                }
            }
        });
    }

    private void setupListeners() {
        // Tab切换监听
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String tabText = tab.getText() != null ? tab.getText().toString() : "全部";
                if (tabText.equals("全部")) {
                    currentBoard = "全部";
                    currentTag = null;
                    loadPosts();
                } else if (Arrays.asList("热门", "最新", "水区").contains(tabText)) {
                    currentBoard = tabText;
                    currentTag = null;
                    loadPosts();
                } else {
                    currentTag = tabText;
                    currentBoard = null;
                    loadPostsByTag();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        // 发帖按钮
        fabAddPost.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreatePostActivity.class);
            startActivity(intent);
        });
    }

    private void loadPosts() {
        if (currentBoard == null || currentBoard.equals("全部")) {
            if (currentSort.equals("create_time")) {
                postViewModel.getAllPostsByCreateTime().observe(this, posts -> {
                    if (posts != null) {
                        adapter.setPostList(posts);
                    }
                });
            } else {
                postViewModel.getAllPostsByReplyTime().observe(this, posts -> {
                    if (posts != null) {
                        adapter.setPostList(posts);
                    }
                });
            }
        } else {
            postViewModel.getPostsByBoard(currentBoard).observe(this, posts -> {
                if (posts != null) {
                    adapter.setPostList(posts);
                }
            });
        }
    }

    private void loadPostsByTag() {
        if (currentTag != null) {
            postViewModel.getPostsByTag(currentTag).observe(this, posts -> {
                if (posts != null) {
                    adapter.setPostList(posts);
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 刷新列表
        if (adapter != null && adapter.getItemCount() > 0) {
            adapter.notifyDataSetChanged();
        }
    }
}

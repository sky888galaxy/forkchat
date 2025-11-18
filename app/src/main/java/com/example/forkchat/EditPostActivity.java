package com.example.forkchat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.forkchat.model.Post;
import com.example.forkchat.utils.ImageUtils;
import com.example.forkchat.viewmodel.PostViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

/**
 * 编辑帖子Activity
 */
public class EditPostActivity extends AppCompatActivity {
    private MaterialToolbar toolbar;
    private TextInputEditText etTitle, etContent;
    private ImageView ivCover;
    private Button btnSelectImage, btnRemoveImage, btnSave;

    private PostViewModel postViewModel;
    private long postId;
    private Post currentPost;
    private String coverImagePath = null;

    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        postId = getIntent().getLongExtra("postId", -1);
        if (postId == -1) {
            finish();
            return;
        }

        initViews();
        initViewModels();
        setupToolbar();
        setupImagePicker();
        loadPost();
        setupListeners();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);
        ivCover = findViewById(R.id.ivCover);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnRemoveImage = findViewById(R.id.btnRemoveImage);
        btnSave = findViewById(R.id.btnSave);
    }

    private void initViewModels() {
        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void setupImagePicker() {
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        if (imageUri != null) {
                            coverImagePath = ImageUtils.saveImageToLocal(this, imageUri);
                            if (coverImagePath != null) {
                                ivCover.setVisibility(android.view.View.VISIBLE);
                                btnSelectImage.setVisibility(android.view.View.GONE);
                                btnRemoveImage.setVisibility(android.view.View.VISIBLE);
                                ImageUtils.loadImage(this, coverImagePath, ivCover);
                            }
                        }
                    }
                }
        );
    }

    private void loadPost() {
        postViewModel.getPostById(postId).observe(this, post -> {
            if (post != null) {
                currentPost = post;
                displayPost(post);
            }
        });
    }

    private void displayPost(Post post) {
        etTitle.setText(post.getTitle());
        etContent.setText(post.getContent());
        
        coverImagePath = post.getCoverImage();
        if (coverImagePath != null && !coverImagePath.isEmpty()) {
            ivCover.setVisibility(android.view.View.VISIBLE);
            btnSelectImage.setVisibility(android.view.View.GONE);
            btnRemoveImage.setVisibility(android.view.View.VISIBLE);
            ImageUtils.loadImage(this, coverImagePath, ivCover);
        }
    }

    private void setupListeners() {
        btnSelectImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
        });

        btnRemoveImage.setOnClickListener(v -> {
            coverImagePath = null;
            ivCover.setVisibility(android.view.View.GONE);
            btnSelectImage.setVisibility(android.view.View.VISIBLE);
            btnRemoveImage.setVisibility(android.view.View.GONE);
        });

        btnSave.setOnClickListener(v -> savePost());
    }

    private void savePost() {
        String title = etTitle.getText() != null ? etTitle.getText().toString().trim() : "";
        String content = etContent.getText() != null ? etContent.getText().toString().trim() : "";

        if (title.isEmpty()) {
            Toast.makeText(this, "请输入标题", Toast.LENGTH_SHORT).show();
            return;
        }

        if (content.isEmpty()) {
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentPost != null) {
            currentPost.setTitle(title);
            currentPost.setContent(content);
            currentPost.setCoverImage(coverImagePath);

            postViewModel.updatePost(currentPost);
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}


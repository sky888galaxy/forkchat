package com.example.forkchat.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.forkchat.R;
import com.example.forkchat.model.Post;
import com.example.forkchat.model.Tag;
import com.example.forkchat.utils.ImageUtils;
import com.example.forkchat.viewmodel.PostViewModel;
import com.example.forkchat.viewmodel.TagViewModel;
import com.example.forkchat.viewmodel.UserViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 创建帖子Activity
 */
public class CreatePostActivity extends AppCompatActivity {
    private MaterialToolbar toolbar;
    private AutoCompleteTextView actvBoard;
    private TextInputEditText etTitle, etContent;
    private ChipGroup chipGroupTags;
    private ImageView ivCover;
    private Button btnSelectImage, btnRemoveImage;
    private Button btnBold, btnItalic, btnLink;
    private Button btnPublish;

    private PostViewModel postViewModel;
    private TagViewModel tagViewModel;
    private UserViewModel userViewModel;

    private String selectedBoard = "热门";
    private List<String> selectedTags = new ArrayList<>();
    private String coverImagePath = null;
    private long currentUserId;

    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        initViews();
        initViewModels();
        setupToolbar();
        setupBoardSpinner();
        setupTags();
        setupImagePicker();
        setupListeners();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        actvBoard = findViewById(R.id.actvBoard);
        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);
        chipGroupTags = findViewById(R.id.chipGroupTags);
        ivCover = findViewById(R.id.ivCover);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnRemoveImage = findViewById(R.id.btnRemoveImage);
        btnBold = findViewById(R.id.btnBold);
        btnItalic = findViewById(R.id.btnItalic);
        btnLink = findViewById(R.id.btnLink);
        btnPublish = findViewById(R.id.btnPublish);
    }

    private void initViewModels() {
        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        tagViewModel = new ViewModelProvider(this).get(TagViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        currentUserId = userViewModel.getCurrentUserIdValue();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void setupBoardSpinner() {
        List<String> boards = Arrays.asList("热门", "最新", "水区", "讨论", "学习");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, boards);
        actvBoard.setAdapter(adapter);
        actvBoard.setText(selectedBoard, false);
        actvBoard.setOnItemClickListener((parent, view, position, id) -> {
            selectedBoard = boards.get(position);
        });
    }

    private void setupTags() {
        tagViewModel.getAllTagsByPopularity().observe(this, tags -> {
            if (tags != null) {
                chipGroupTags.removeAllViews();
                for (Tag tag : tags) {
                    Chip chip = new Chip(this);
                    chip.setText(tag.getName());
                    chip.setCheckable(true);
                    chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
                        if (isChecked) {
                            if (selectedTags.size() >= 3) {
                                chip.setChecked(false);
                                Toast.makeText(this, "最多只能选择3个标签", Toast.LENGTH_SHORT).show();
                            } else {
                                selectedTags.add(tag.getName());
                            }
                        } else {
                            selectedTags.remove(tag.getName());
                        }
                    });
                    chipGroupTags.addView(chip);
                }
            }
        });
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

        btnBold.setOnClickListener(v -> applyTextStyle(android.graphics.Typeface.BOLD));
        btnItalic.setOnClickListener(v -> applyTextStyle(android.graphics.Typeface.ITALIC));
        btnLink.setOnClickListener(v -> insertLink());

        btnPublish.setOnClickListener(v -> publishPost());
    }

    private void applyTextStyle(int style) {
        int start = etContent.getSelectionStart();
        int end = etContent.getSelectionEnd();

        if (start >= end) {
            Toast.makeText(this, "请先选择要格式化的文本", Toast.LENGTH_SHORT).show();
            return;
        }

        SpannableStringBuilder ssb = new SpannableStringBuilder(etContent.getText());
        ssb.setSpan(new StyleSpan(style), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        etContent.setText(ssb);
        etContent.setSelection(end);
    }

    private void insertLink() {
        int position = etContent.getSelectionStart();
        String linkTemplate = "[链接文本](https://example.com)";

        SpannableStringBuilder ssb = new SpannableStringBuilder(etContent.getText());
        ssb.insert(position, linkTemplate);
        etContent.setText(ssb);
        etContent.setSelection(position + 1, position + 5); // 选中"链接文本"
    }

    private void publishPost() {
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

        Post post = new Post(title, content, currentUserId, "当前用户", selectedBoard);

        if (coverImagePath != null) {
            post.setCoverImage(coverImagePath);
        }

        if (!selectedTags.isEmpty()) {
            post.setTags(String.join(",", selectedTags));
        }

        postViewModel.insertPost(post, postId -> {
            runOnUiThread(() -> {
                Toast.makeText(this, "发布成功", Toast.LENGTH_SHORT).show();
                finish();
            });
        });
    }
}
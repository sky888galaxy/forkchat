package com.example.forkchat.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.forkchat.R;
import com.example.forkchat.model.User;
import com.example.forkchat.viewmodel.UserViewModel;

public class EditProfileActivity extends AppCompatActivity {

    private UserViewModel userViewModel;

    private ImageView avatarImageView;
    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText bioEditText;
    private Button saveButton;
    private Button cancelButton;
    private Button changeAvatarButton;
    private ProgressBar progressBar;

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        initViews();
        initViewModel();
        setupClickListeners();
        observeViewModel();
        loadUserData();
    }

    private void initViews() {
        avatarImageView = findViewById(R.id.iv_avatar);
        usernameEditText = findViewById(R.id.et_username);
        emailEditText = findViewById(R.id.et_email);
        bioEditText = findViewById(R.id.et_bio);
        saveButton = findViewById(R.id.btn_save);
        cancelButton = findViewById(R.id.btn_cancel);
        changeAvatarButton = findViewById(R.id.btn_change_avatar);
        progressBar = findViewById(R.id.progress_bar);
    }

    private void initViewModel() {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    private void setupClickListeners() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserProfile();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        changeAvatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeAvatar();
            }
        });
    }

    private void observeViewModel() {
        userViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                saveButton.setEnabled(!isLoading);
            }
        });

        userViewModel.getUpdateSuccess().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean success) {
                if (success != null && success) {
                    Toast.makeText(EditProfileActivity.this, "资料更新成功", Toast.LENGTH_SHORT).show();
                    setResult(Activity.RESULT_OK);
                    finish();
                }
            }
        });

        userViewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                if (errorMessage != null) {
                    Toast.makeText(EditProfileActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void loadUserData() {
        userViewModel.getCurrentUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    usernameEditText.setText(user.getUsername());
                    emailEditText.setText(user.getEmail());
                    bioEditText.setText(user.getBio() != null ? user.getBio() : "");
                    // 这里可以加载用户当前头像
                }
            }
        });
    }

    private void saveUserProfile() {
        String username = usernameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String bio = bioEditText.getText().toString().trim();

        if (username.isEmpty()) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (email.isEmpty()) {
            Toast.makeText(this, "邮箱不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        User currentUser = userViewModel.getCurrentUser().getValue();
        if (currentUser != null) {
            currentUser.setUsername(username);
            currentUser.setEmail(email);
            currentUser.setBio(bio);
            userViewModel.updateUserProfile(currentUser);
        }
    }

    private void changeAvatar() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            // 处理选择的图片
            // 实际开发中需要处理图片裁剪、压缩等
            Toast.makeText(this, "头像选择成功", Toast.LENGTH_SHORT).show();
        }
    }
}
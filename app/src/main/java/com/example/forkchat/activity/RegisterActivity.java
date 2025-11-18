package com.example.forkchat.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.forkchat.R;
import com.example.forkchat.database.TiebaDatabase;
import com.example.forkchat.model.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername, etEmail, etPassword, etConfirmPassword;
    private Button btnRegister;
    private TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        etUsername = findViewById(R.id.et_username);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        btnRegister = findViewById(R.id.btn_register);
        tvLogin = findViewById(R.id.tv_login);
    }

    private void setupClickListeners() {
        btnRegister.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.length() < 6) {
                Toast.makeText(this, "密码长度至少6位", Toast.LENGTH_SHORT).show();
                return;
            }

            // 注册用户
            registerUser(username, email, password);
        });

        tvLogin.setOnClickListener(v -> {
            finish();
        });
    }

    private void registerUser(String username, String email, String password) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    TiebaDatabase database = TiebaDatabase.getDatabase(RegisterActivity.this);

                    // 检查用户名是否已存在
                    User existingUser = database.userDao().getUserByUsername(username);
                    if (existingUser != null) {
                        return "用户名已存在";
                    }

                    // 检查邮箱是否已存在
                    existingUser = database.userDao().getUserByEmail(email);
                    if (existingUser != null) {
                        return "邮箱已存在";
                    }

                    // 创建新用户
                    User newUser = new User();
                    newUser.setUsername(username);
                    newUser.setEmail(email);
                    newUser.setPassword(password);

                    // 设置时间
                    String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                            .format(new Date());
                    newUser.setCreateTime(currentTime);
                    newUser.setRegistrationDate(currentTime);
                    newUser.setBio("这个人很懒，什么都没有写～");
                    newUser.setAvatar(""); // 可以设置默认头像

                    long userId = database.userDao().insert(newUser);

                    if (userId > 0) {
                        return "success";
                    } else {
                        return "注册失败，请重试";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return "注册失败: " + e.getMessage();
                }
            }

            @Override
            protected void onPostExecute(String result) {
                if ("success".equals(result)) {
                    Toast.makeText(RegisterActivity.this, "注册成功，请登录", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, result, Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }
}
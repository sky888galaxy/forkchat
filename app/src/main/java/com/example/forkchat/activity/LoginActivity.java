package com.example.forkchat.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.forkchat.MainActivity;
import com.example.forkchat.R;
import com.example.forkchat.database.TiebaDatabase;
import com.example.forkchat.model.User;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvRegister;

    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "user_prefs";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // 检查是否已登录
        if (isUserLoggedIn()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        tvRegister = findViewById(R.id.tv_register);
    }

    private void setupClickListeners() {
        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "请输入邮箱和密码", Toast.LENGTH_SHORT).show();
                return;
            }

            // 验证登录
            loginUser(email, password);
        });

        tvRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }

    private void loginUser(String email, String password) {
        new AsyncTask<Void, Void, User>() {
            @Override
            protected User doInBackground(Void... voids) {
                try {
                    TiebaDatabase database = TiebaDatabase.getDatabase(LoginActivity.this);
                    return database.userDao().getUserByEmailAndPassword(email, password);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(User user) {
                if (user != null) {
                    // 登录成功，保存用户会话
                    saveUserSession(user);
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "邮箱或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    private void saveUserSession(User user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(KEY_USER_ID, user.getId());
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.apply();
    }

    private boolean isUserLoggedIn() {
        return sharedPreferences.getLong(KEY_USER_ID, -1) != -1;
    }

    // 提供静态方法供其他Activity获取当前用户信息
    public static long getCurrentUserId(SharedPreferences prefs) {
        return prefs.getLong(KEY_USER_ID, -1);
    }

    public static String getCurrentUsername(SharedPreferences prefs) {
        return prefs.getString(KEY_USERNAME, "");
    }

    public static String getCurrentUserEmail(SharedPreferences prefs) {
        return prefs.getString(KEY_EMAIL, "");
    }
}
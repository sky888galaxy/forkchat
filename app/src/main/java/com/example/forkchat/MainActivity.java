package com.example.forkchat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.forkchat.activity.UserProfileActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 添加跳转到用户资料页面的按钮
        Button btnToProfile = findViewById(R.id.button); // 使用默认的button
        btnToProfile.setText("进入个人中心");
        btnToProfile.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
            startActivity(intent);
        });
    }
}
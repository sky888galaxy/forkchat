package com.example.forkchat;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 测试Activity - 用于调试
 */
public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        TextView textView = new TextView(this);
        textView.setText("贴吧应用测试");
        textView.setTextSize(24);
        textView.setPadding(50, 50, 50, 50);
        
        setContentView(textView);
    }
}


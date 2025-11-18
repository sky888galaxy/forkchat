package com.example.forkchat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.forkchat.R;
import com.example.forkchat.databinding.ActivityOpinionDetailBinding;
import com.example.forkchat.activity.detail.DetailRepository;
import com.example.forkchat.activity.detail.Opinion;
import com.example.forkchat.activity.detail.OpinionAdapter;
import com.example.forkchat.activity.detail.Reply;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

/**
 * 观点详情页 - 单列完整展示
 */
public class OpinionDetailActivity extends AppCompatActivity {

    public static final String EXTRA_OPINION_TYPE = "opinion_type";
    public static final String EXTRA_POST_TITLE = "post_title";

    private ActivityOpinionDetailBinding binding;
    private OpinionAdapter opinionAdapter;
    private final ArrayList<Opinion> opinions = new ArrayList<>();
    private Opinion.Type currentType;
    private String postTitle;
    private Opinion targetOpinion; // 当前要回复的目标观点

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOpinionDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            binding.inputBar.setPadding(
                    binding.inputBar.getPaddingLeft(),
                    binding.inputBar.getPaddingTop(),
                    binding.inputBar.getPaddingRight(),
                    systemBars.bottom
            );
            return insets;
        });

        // 获取传递的参数
        Intent intent = getIntent();
        String typeStr = intent.getStringExtra(EXTRA_OPINION_TYPE);
        currentType = Opinion.Type.valueOf(typeStr != null ? typeStr : "SUPPLEMENT");
        postTitle = intent.getStringExtra(EXTRA_POST_TITLE);

        setupToolbar();
        setupRecyclerView();
        loadData();
        setupListeners();
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        binding.toolbarTitle.setText(postTitle);
        binding.toolbarType.setText(currentType == Opinion.Type.SUPPLEMENT ? 
                R.string.tab_supplement : R.string.tab_question);
        
        int color = currentType == Opinion.Type.SUPPLEMENT ? 
                getColor(R.color.primary_pink) : getColor(R.color.primary_orange);
        binding.toolbarType.setTextColor(color);

        // 根据类型设置背景色
        int bgColor = currentType == Opinion.Type.SUPPLEMENT ?
                getColor(R.color.primary_pink_light) : getColor(R.color.primary_orange_light);
        binding.getRoot().setBackgroundColor(bgColor);

        binding.toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void setupRecyclerView() {
        opinionAdapter = new OpinionAdapter(new OpinionAdapter.OnOpinionListener() {
            @Override
            public void onLikeClick(Opinion opinion) {
                handleLike(opinion);
            }

            @Override
            public void onReplyClick(Opinion opinion) {
                // 设置回复目标
                targetOpinion = opinion;
                binding.replyInput.setHint("回复 " + opinion.getAuthorName() + "...");
                binding.replyInput.requestFocus();
            }

            @Override
            public void onExpandClick(Opinion opinion) {
                opinion.setExpanded(!opinion.isExpanded());
                opinionAdapter.updateOpinion(opinion);
            }

            @Override
            public void onDeriveClick(Opinion opinion) {
                Snackbar.make(binding.getRoot(), R.string.toast_derive_todo,
                        Snackbar.LENGTH_SHORT).show();
            }
        });

        binding.opinionList.setLayoutManager(new LinearLayoutManager(this));
        binding.opinionList.setAdapter(opinionAdapter);
    }

    private void loadData() {
        if (currentType == Opinion.Type.SUPPLEMENT) {
            opinions.addAll(DetailRepository.loadSupplementOpinions());
        } else {
            opinions.addAll(DetailRepository.loadQuestionOpinions());
        }
        opinionAdapter.submitList(opinions);
    }

    private void setupListeners() {
        binding.submitReplyButton.setOnClickListener(v -> submitReply());
    }

    private void handleLike(Opinion opinion) {
        opinion.setLiked(!opinion.isLiked());
        if (opinion.isLiked()) {
            opinion.setLikeCount(opinion.getLikeCount() + 1);
            Snackbar.make(binding.getRoot(), R.string.toast_liked, Snackbar.LENGTH_SHORT).show();
        } else {
            opinion.setLikeCount(opinion.getLikeCount() - 1);
            Snackbar.make(binding.getRoot(), R.string.toast_unliked, Snackbar.LENGTH_SHORT).show();
        }
        opinionAdapter.updateOpinion(opinion);
    }

    private void submitReply() {
        CharSequence input = binding.replyInput.getText();
        if (TextUtils.isEmpty(input)) {
            Snackbar.make(binding.getRoot(), R.string.toast_reply_empty, 
                    Snackbar.LENGTH_SHORT).show();
            return;
        }

        String content = input.toString().trim();
        if (content.isEmpty()) {
            Snackbar.make(binding.getRoot(), R.string.toast_reply_empty, 
                    Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (content.length() > 200) {
            Snackbar.make(binding.getRoot(), R.string.toast_reply_too_long, 
                    Snackbar.LENGTH_SHORT).show();
            return;
        }

        // 获取选中的回复类型
        Reply.Type replyType = binding.replyTypeToggle.getCheckedButtonId() == R.id.btnSupplement
                ? Reply.Type.SUPPLEMENT 
                : Reply.Type.QUESTION;

        // 如果没有指定回复目标，默认回复第一个观点
        Opinion target = targetOpinion != null ? targetOpinion : 
                (!opinions.isEmpty() ? opinions.get(0) : null);
        
        if (target != null) {
            Reply newReply = DetailRepository.createUserReply(content, replyType);
            target.addReply(newReply);
            opinionAdapter.updateOpinion(target);
            
            binding.replyInput.setText("");
            binding.replyInput.setHint(R.string.hint_add_reply);
            targetOpinion = null; // 清除回复目标
            Snackbar.make(binding.getRoot(), R.string.toast_reply_added, 
                    Snackbar.LENGTH_SHORT).show();
        }
    }
}

package com.example.forkchat.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.forkchat.R;
import com.example.forkchat.TiebaApplication;
import com.example.forkchat.databinding.ActivityMainV2Binding;
import com.example.forkchat.activity.detail.DetailRepository;
import com.example.forkchat.activity.detail.Opinion;
import com.example.forkchat.activity.detail.Post;
import com.google.android.material.chip.Chip;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ActivityMainV2Binding binding;
    private Post currentPost;
    private final ArrayList<Opinion> supplementOpinions = new ArrayList<>();
    private final ArrayList<Opinion> questionOpinions = new ArrayList<>();
    private OpinionPreviewAdapter supplementAdapter;
    private OpinionPreviewAdapter questionAdapter;
    private boolean isDescriptionExpanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainV2Binding.inflate(getLayoutInflater());
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

        setupToolbar();
        setupAdapters();
        loadData();
        bindPost();
        setupListeners();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    private void setupToolbar() {
        setSupportActionBar(binding.detailToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        binding.detailToolbar.setNavigationOnClickListener(v -> finish());
    }

    private void setupAdapters() {
        // 补充列表适配器
        supplementAdapter = new OpinionPreviewAdapter(opinion -> {
            // 点击进入补充详情界面
            Intent intent = new Intent(this, OpinionDetailActivity.class);
            intent.putExtra(OpinionDetailActivity.EXTRA_OPINION_TYPE,
                    Opinion.Type.SUPPLEMENT.name());
            intent.putExtra(OpinionDetailActivity.EXTRA_POST_TITLE, currentPost.getTitle());
            startActivity(intent);
        });

        binding.supplementPreviewList.setLayoutManager(new LinearLayoutManager(this));
        binding.supplementPreviewList.setAdapter(supplementAdapter);


        questionAdapter = new OpinionPreviewAdapter(opinion -> {

            Intent intent = new Intent(this, OpinionDetailActivity.class);
            intent.putExtra(OpinionDetailActivity.EXTRA_OPINION_TYPE,
                    Opinion.Type.QUESTION.name());
            intent.putExtra(OpinionDetailActivity.EXTRA_POST_TITLE, currentPost.getTitle());
            startActivity(intent);
        });

        binding.questionPreviewList.setLayoutManager(new LinearLayoutManager(this));
        binding.questionPreviewList.setAdapter(questionAdapter);
    }

    private void loadData() {
        currentPost = DetailRepository.loadFeaturedPost();
        supplementOpinions.addAll(DetailRepository.loadSupplementOpinions());
        questionOpinions.addAll(DetailRepository.loadQuestionOpinions());
    }

    private void bindPost() {

        binding.toolbarTitle.setText(currentPost.getTitle());
        binding.toolbarSubtitle.setText("发布者：" + currentPost.getAuthorName() +
                " · " + currentPost.getPublishTime());


        renderTags(currentPost.getTags());


        binding.postBody.setText(currentPost.getBody());


        binding.viewCount.setText(getString(R.string.post_view_count, currentPost.getViewCount()));
        binding.likeCount.setText(getString(R.string.post_like_count, currentPost.getLikeCount()));


        binding.supplementCount.setText(supplementOpinions.size() + "条观点");
        binding.questionCount.setText(questionOpinions.size() + "条观点");
        supplementAdapter.submitList(supplementOpinions);
        questionAdapter.submitList(questionOpinions);


        updateLikeButton();
        updateCollectButton();
    }

    private void renderTags(@NonNull List<String> tags) {
        binding.tagGroup.removeAllViews();
        for (String tag : tags) {
            Chip chip = new Chip(this);
            chip.setText(tag);
            chip.setCheckable(false);
            chip.setChipBackgroundColor(ColorStateList.valueOf(
                    MaterialColors.getColor(chip, com.google.android.material.R.attr.colorSecondaryContainer)));
            binding.tagGroup.addView(chip);
        }
    }

    private void setupListeners() {

        binding.expandDescButton.setOnClickListener(v -> {
            isDescriptionExpanded = !isDescriptionExpanded;
            if (isDescriptionExpanded) {
                binding.postBody.setMaxLines(Integer.MAX_VALUE);
                binding.expandDescButton.setText(R.string.action_collapse_description);
            } else {
                binding.postBody.setMaxLines(3);
                binding.expandDescButton.setText(R.string.action_expand_description);
            }
        });


        binding.likeButton.setOnClickListener(v -> {
            currentPost.setLiked(!currentPost.isLiked());
            if (currentPost.isLiked()) {
                currentPost.setLikeCount(currentPost.getLikeCount() + 1);
                Snackbar.make(binding.getRoot(), R.string.toast_liked, Snackbar.LENGTH_SHORT).show();
            } else {
                currentPost.setLikeCount(currentPost.getLikeCount() - 1);
                Snackbar.make(binding.getRoot(), R.string.toast_unliked, Snackbar.LENGTH_SHORT).show();
            }
            updateLikeButton();
            binding.likeCount.setText(getString(R.string.post_like_count, currentPost.getLikeCount()));
        });


        binding.collectButton.setOnClickListener(v -> {
            currentPost.setCollected(!currentPost.isCollected());
            Snackbar.make(binding.getRoot(),
                    currentPost.isCollected() ? R.string.toast_collected : R.string.toast_uncollected,
                    Snackbar.LENGTH_SHORT).show();
            updateCollectButton();
        });


        binding.supplementCard.setOnClickListener(v -> {
            Intent intent = new Intent(this, OpinionDetailActivity.class);
            intent.putExtra(OpinionDetailActivity.EXTRA_OPINION_TYPE,
                    Opinion.Type.SUPPLEMENT.name());
            intent.putExtra(OpinionDetailActivity.EXTRA_POST_TITLE, currentPost.getTitle());
            startActivity(intent);
        });


        binding.questionCard.setOnClickListener(v -> {
            Intent intent = new Intent(this, OpinionDetailActivity.class);
            intent.putExtra(OpinionDetailActivity.EXTRA_OPINION_TYPE,
                    Opinion.Type.QUESTION.name());
            intent.putExtra(OpinionDetailActivity.EXTRA_POST_TITLE, currentPost.getTitle());
            startActivity(intent);
        });


        binding.submitOpinionButton.setOnClickListener(v -> submitOpinion());
    }

    private void updateLikeButton() {
        if (currentPost.isLiked()) {
            binding.likeButton.setIconTint(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.primary_pink)));
            binding.likeButton.setTextColor(ContextCompat.getColor(this, R.color.primary_pink));
        } else {
            binding.likeButton.setIconTint(null);
            binding.likeButton.setTextColor(ContextCompat.getColor(this, R.color.text_primary));
        }
    }

    private void updateCollectButton() {
        if (currentPost.isCollected()) {
            binding.collectButton.setText(R.string.action_collected);
            binding.collectButton.setIconTint(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.primary_orange)));
            binding.collectButton.setTextColor(ContextCompat.getColor(this, R.color.primary_orange));
        } else {
            binding.collectButton.setText(R.string.action_collect);
            binding.collectButton.setIconTint(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.primary_orange)));
            binding.collectButton.setTextColor(ContextCompat.getColor(this, R.color.text_primary));
        }
    }

    private void submitOpinion() {
        CharSequence input = binding.opinionInput.getText();
        if (TextUtils.isEmpty(input)) {
            Snackbar.make(binding.getRoot(), R.string.toast_opinion_empty,
                    Snackbar.LENGTH_SHORT).show();
            return;
        }

        String content = input.toString().trim();
        if (content.isEmpty()) {
            Snackbar.make(binding.getRoot(), R.string.toast_opinion_empty,
                    Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (content.length() > 500) {
            Snackbar.make(binding.getRoot(), R.string.toast_opinion_too_long,
                    Snackbar.LENGTH_SHORT).show();
            return;
        }


        int checkedId = binding.opinionTypeToggle.getCheckedButtonId();
        Opinion.Type type = (checkedId == R.id.supplementToggle) ?
                Opinion.Type.SUPPLEMENT : Opinion.Type.QUESTION;


        Opinion newOpinion = DetailRepository.createUserOpinion(type, content);


        if (type == Opinion.Type.SUPPLEMENT) {
            supplementOpinions.add(0, newOpinion);
            currentPost.setSupplementCount(currentPost.getSupplementCount() + 1);
            supplementAdapter.submitList(supplementOpinions);
            binding.supplementCount.setText(supplementOpinions.size() + "条观点");
        } else {
            questionOpinions.add(0, newOpinion);
            currentPost.setQuestionCount(currentPost.getQuestionCount() + 1);
            questionAdapter.submitList(questionOpinions);
            binding.questionCount.setText(questionOpinions.size() + "条观点");
        }

        // 清空输入框
        binding.opinionInput.setText("");

        Snackbar.make(binding.getRoot(), R.string.toast_opinion_added,
                Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 从详情页返回时刷新数据
        refreshData();
    }

    private void refreshData() {
        supplementOpinions.clear();
        questionOpinions.clear();
        supplementOpinions.addAll(DetailRepository.loadSupplementOpinions());
        questionOpinions.addAll(DetailRepository.loadQuestionOpinions());

        supplementAdapter.submitList(supplementOpinions);
        questionAdapter.submitList(questionOpinions);
        binding.supplementCount.setText(supplementOpinions.size() + "条观点");
        binding.questionCount.setText(questionOpinions.size() + "条观点");
    }
}
package com.example.forkchat.activity.detail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forkchat.R;
import com.example.forkchat.adapter.ReplyAdapter;
import com.example.forkchat.databinding.ItemOpinionBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * 观点适配器 - 支持补充/质疑观点展示和二级短评
 */
public class OpinionAdapter extends RecyclerView.Adapter<OpinionAdapter.OpinionViewHolder> {

    public interface OnOpinionListener {
        void onLikeClick(Opinion opinion);
        void onReplyClick(Opinion opinion);
        void onExpandClick(Opinion opinion);
        void onDeriveClick(Opinion opinion);
    }

    private final List<Opinion> opinions = new ArrayList<>();
    private final OnOpinionListener listener;

    public OpinionAdapter(OnOpinionListener listener) {
        this.listener = listener;
    }

    public void submitList(@NonNull List<Opinion> newOpinions) {
        opinions.clear();
        opinions.addAll(newOpinions);
        notifyDataSetChanged();
    }

    public void addOpinion(@NonNull Opinion opinion) {
        opinions.add(0, opinion);
        notifyItemInserted(0);
    }

    public void updateOpinion(Opinion opinion) {
        int index = findOpinionIndex(opinion.getId());
        if (index != -1) {
            opinions.set(index, opinion);
            notifyItemChanged(index);
        }
    }

    private int findOpinionIndex(String id) {
        for (int i = 0; i < opinions.size(); i++) {
            if (opinions.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    @NonNull
    @Override
    public OpinionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemOpinionBinding binding = ItemOpinionBinding.inflate(inflater, parent, false);
        return new OpinionViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull OpinionViewHolder holder, int position) {
        holder.bind(opinions.get(position));
    }

    @Override
    public int getItemCount() {
        return opinions.size();
    }

    static class OpinionViewHolder extends RecyclerView.ViewHolder {
        private final ItemOpinionBinding binding;
        private final OnOpinionListener listener;
        private final ReplyAdapter replyAdapter;

        OpinionViewHolder(ItemOpinionBinding binding, OnOpinionListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
            
            // 设置短评列表
            replyAdapter = new ReplyAdapter();
            binding.replyRecycler.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
            binding.replyRecycler.setAdapter(replyAdapter);
            binding.replyRecycler.setNestedScrollingEnabled(false);
        }

        void bind(Opinion opinion) {
            binding.authorName.setText(opinion.getAuthorName());
            binding.publishTime.setText(opinion.getPublishTime());
            binding.opinionContent.setText(opinion.getContent());
            binding.likeCount.setText(String.valueOf(opinion.getLikeCount()));
            binding.replyCount.setText(String.valueOf(opinion.getReplyCount()));

            // 点赞状态
            int likeColor = opinion.isLiked() 
                    ? binding.getRoot().getContext().getColor(R.color.reddit_orange)
                    : binding.getRoot().getContext().getColor(R.color.text_secondary);
            binding.likeIcon.setColorFilter(likeColor);
            binding.likeCount.setTextColor(likeColor);

            // 短评展开/收起
            boolean hasReplies = opinion.getReplyCount() > 0;
            binding.expandButton.setVisibility(hasReplies ? View.VISIBLE : View.GONE);
            binding.replyRecycler.setVisibility(
                    opinion.isExpanded() && hasReplies ? View.VISIBLE : View.GONE);
            binding.expandButton.setText(opinion.isExpanded() ? "收起短评" : "展开" + opinion.getReplyCount() + "条短评");

            if (opinion.isExpanded()) {
                replyAdapter.submitList(opinion.getReplies());
            }

            // 点击事件
            binding.likeButton.setOnClickListener(v -> listener.onLikeClick(opinion));
            binding.replyButton.setOnClickListener(v -> listener.onReplyClick(opinion));
            binding.expandButton.setOnClickListener(v -> listener.onExpandClick(opinion));
            binding.deriveButton.setOnClickListener(v -> listener.onDeriveClick(opinion));
        }
    }
}

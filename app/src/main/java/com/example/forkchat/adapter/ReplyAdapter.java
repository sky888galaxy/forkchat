package com.example.forkchat.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forkchat.R;
import com.example.forkchat.databinding.ItemReplyTypedBinding;
import com.example.forkchat.activity.detail.Reply;

import java.util.ArrayList;
import java.util.List;

/**
 * 短评适配器 - 展示二级短评列表（支持类型标签）
 */
public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.ReplyViewHolder> {

    private final List<Reply> replies = new ArrayList<>();

    public void submitList(@NonNull List<Reply> newReplies) {
        replies.clear();
        replies.addAll(newReplies);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReplyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemReplyTypedBinding binding = ItemReplyTypedBinding.inflate(inflater, parent, false);
        return new ReplyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReplyViewHolder holder, int position) {
        holder.bind(replies.get(position));
    }

    @Override
    public int getItemCount() {
        return replies.size();
    }

    static class ReplyViewHolder extends RecyclerView.ViewHolder {
        private final ItemReplyTypedBinding binding;

        ReplyViewHolder(ItemReplyTypedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Reply reply) {
            // 设置类型标签
            binding.replyType.setText(reply.getType().getLabel());
            int typeColor = reply.getType() == Reply.Type.SUPPLEMENT
                    ? binding.getRoot().getContext().getColor(R.color.primary_pink)
                    : binding.getRoot().getContext().getColor(R.color.primary_orange);
            binding.replyType.setBackgroundColor(typeColor);

            // 根据类型设置卡片背景
            int cardColor = reply.getType() == Reply.Type.SUPPLEMENT
                    ? binding.getRoot().getContext().getColor(R.color.background_supplement_light)
                    : binding.getRoot().getContext().getColor(R.color.background_question_light);
            binding.replyContainer.setBackgroundColor(cardColor);

            binding.replyAuthor.setText(reply.getAuthorName());
            binding.replyTime.setText(reply.getPublishTime());
            binding.replyContent.setText(reply.getContent());
            binding.replyLikeCount.setText("♡ " + reply.getLikeCount());
        }
    }
}

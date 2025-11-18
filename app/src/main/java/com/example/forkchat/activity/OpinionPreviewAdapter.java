package com.example.forkchat.activity;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forkchat.databinding.ItemOpinionPreviewBinding;
import com.example.forkchat.activity.detail.Opinion;

import java.util.ArrayList;
import java.util.List;

/**
 * 观点预览适配器 - 用于双列概要显示
 */
public class OpinionPreviewAdapter extends RecyclerView.Adapter<OpinionPreviewAdapter.PreviewViewHolder> {

    public interface OnPreviewClickListener {
        void onPreviewClick(Opinion opinion);
    }

    private final List<Opinion> opinions = new ArrayList<>();
    private final OnPreviewClickListener listener;

    public OpinionPreviewAdapter(OnPreviewClickListener listener) {
        this.listener = listener;
    }

    public void submitList(@NonNull List<Opinion> newOpinions) {
        opinions.clear();
        // 只显示前3条
        int count = Math.min(newOpinions.size(), 3);
        for (int i = 0; i < count; i++) {
            opinions.add(newOpinions.get(i));
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PreviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemOpinionPreviewBinding binding = ItemOpinionPreviewBinding.inflate(inflater, parent, false);
        return new PreviewViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PreviewViewHolder holder, int position) {
        holder.bind(opinions.get(position));
    }

    @Override
    public int getItemCount() {
        return opinions.size();
    }

    class PreviewViewHolder extends RecyclerView.ViewHolder {
        private final ItemOpinionPreviewBinding binding;

        PreviewViewHolder(ItemOpinionPreviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Opinion opinion) {
            binding.previewAuthor.setText(opinion.getAuthorName());
            binding.previewTime.setText(opinion.getPublishTime());
            binding.previewContent.setText(opinion.getContent());
            binding.previewLikeCount.setText("♡ " + opinion.getLikeCount());
            binding.previewReplyCount.setText("💬 " + opinion.getReplies().size());

            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) {
                    listener.onPreviewClick(opinion);
                }
            });
        }
    }
}

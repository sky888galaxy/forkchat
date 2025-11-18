package com.example.forkchat.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forkchat.R;
import com.example.forkchat.model.Topic;
import com.example.forkchat.fc.OpinionDetailActivity;
import com.example.forkchat.fc.detail.Opinion;

import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder> {

    private List<Topic> topics;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Topic topic);
    }

    public TopicAdapter(List<Topic> topics, OnItemClickListener listener) {
        this.topics = topics;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_topic, parent, false);
        return new TopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder holder, int position) {
        Topic topic = topics.get(position);
        holder.bind(topic, listener);
    }

    @Override
    public int getItemCount() {
        return topics != null ? topics.size() : 0;
    }

    public void updateTopics(List<Topic> newTopics) {
        this.topics = newTopics;
        notifyDataSetChanged();
    }

    static class TopicViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView contentTextView;
        private TextView authorTextView;
        private TextView timeTextView;
        private TextView likeCountTextView;
        private TextView commentCountTextView;

        public TopicViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.tv_topic_title);
            contentTextView = itemView.findViewById(R.id.tv_topic_content);
            authorTextView = itemView.findViewById(R.id.tv_author);
            timeTextView = itemView.findViewById(R.id.tv_create_time);
            likeCountTextView = itemView.findViewById(R.id.tv_like_count);
            commentCountTextView = itemView.findViewById(R.id.tv_comment_count);
        }

        public void bind(final Topic topic, final OnItemClickListener listener) {
            titleTextView.setText(topic.getTitle());
            contentTextView.setText(topic.getContent());
            authorTextView.setText(topic.getAuthorName());
            timeTextView.setText(topic.getCreateTime());
            likeCountTextView.setText(String.valueOf(topic.getLikeCount()));
            commentCountTextView.setText(String.valueOf(topic.getCommentCount()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 跳转到FC的观点详情页面
                    Context context = v.getContext();
                    Intent intent = new Intent(context, OpinionDetailActivity.class);
                    intent.putExtra(OpinionDetailActivity.EXTRA_POST_ID, topic.getId());
                    intent.putExtra(OpinionDetailActivity.EXTRA_POST_TITLE, topic.getTitle());
                    intent.putExtra(OpinionDetailActivity.EXTRA_POST_CONTENT, topic.getContent());
                    intent.putExtra(OpinionDetailActivity.EXTRA_OPINION_TYPE, Opinion.Type.SUPPLEMENT.name());
                    context.startActivity(intent);
                    
                    // 同时保留原有的监听器回调
                    if (listener != null) {
                        listener.onItemClick(topic);
                    }
                }
            });
        }
    }
}
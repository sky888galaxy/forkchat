package com.example.forkchat.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forkchat.R;
import com.example.forkchat.model.Topic;

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
                    if (listener != null) {
                        listener.onItemClick(topic);
                    }
                }
            });
        }
    }
}
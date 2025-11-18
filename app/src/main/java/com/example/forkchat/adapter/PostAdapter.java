package com.example.forkchat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forkchat.R;
import com.example.forkchat.model.Post;
import com.example.forkchat.utils.ImageUtils;
import com.example.forkchat.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 帖子列表适配器
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private Context context;
    private List<Post> postList;
    private OnPostClickListener listener;

    public PostAdapter(Context context) {
        this.context = context;
        this.postList = new ArrayList<>();
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
        notifyDataSetChanged();
    }

    public void setOnPostClickListener(OnPostClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = postList.get(position);
        
        holder.tvTitle.setText(post.getTitle());
        holder.tvContent.setText(post.getContent());
        holder.tvAuthor.setText(post.getAuthorName());
        holder.tvTime.setText(TimeUtils.getTimeAgo(post.getCreateTime()));
        holder.tvViewCount.setText("👁️ " + post.getViewCount());
        holder.tvReplyCount.setText("💬 " + post.getReplyCount());
        holder.tvLikeCount.setText("👍 " + post.getLikeCount());

        // 加载头像
        if (post.getAuthorAvatar() != null && !post.getAuthorAvatar().isEmpty()) {
            ImageUtils.loadCircleImage(context, post.getAuthorAvatar(), holder.ivAvatar);
        }

        // 加载封面图
        if (post.getCoverImage() != null && !post.getCoverImage().isEmpty()) {
            holder.ivCover.setVisibility(View.VISIBLE);
            ImageUtils.loadImage(context, post.getCoverImage(), holder.ivCover);
        } else {
            holder.ivCover.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onPostClick(post);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postList != null ? postList.size() : 0;
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvContent, tvAuthor, tvTime;
        TextView tvViewCount, tvReplyCount, tvLikeCount;
        ImageView ivAvatar, ivCover;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvViewCount = itemView.findViewById(R.id.tvViewCount);
            tvReplyCount = itemView.findViewById(R.id.tvReplyCount);
            tvLikeCount = itemView.findViewById(R.id.tvLikeCount);
            ivAvatar = itemView.findViewById(R.id.ivAvatar);
            ivCover = itemView.findViewById(R.id.ivCover);
        }
    }

    public interface OnPostClickListener {
        void onPostClick(Post post);
    }
}


package com.example.forkchat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forkchat.R;
import com.example.forkchat.model.Reply;
import com.example.forkchat.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 子回复(楼中楼)适配器
 */
public class SubReplyAdapter extends RecyclerView.Adapter<SubReplyAdapter.SubReplyViewHolder> {
    private Context context;
    private List<Reply> subReplyList;
    private OnSubReplyClickListener listener;

    public SubReplyAdapter(Context context) {
        this.context = context;
        this.subReplyList = new ArrayList<>();
    }

    public void setSubReplyList(List<Reply> subReplyList) {
        this.subReplyList = subReplyList;
        notifyDataSetChanged();
    }

    public void setOnSubReplyClickListener(OnSubReplyClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public SubReplyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sub_reply, parent, false);
        return new SubReplyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubReplyViewHolder holder, int position) {
        Reply reply = subReplyList.get(position);
        
        holder.tvAuthor.setText(reply.getAuthorName());
        holder.tvTime.setText(TimeUtils.getTimeAgo(reply.getCreateTime()));
        holder.tvContent.setText(reply.getContent());
        
        if (reply.getReplyToUserName() != null && !reply.getReplyToUserName().isEmpty()) {
            holder.tvReplyTo.setVisibility(View.VISIBLE);
            holder.tvReplyTo.setText("回复 @" + reply.getReplyToUserName() + ":");
        } else {
            holder.tvReplyTo.setVisibility(View.GONE);
        }
        
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onSubReplyClick(reply);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subReplyList.size();
    }

    static class SubReplyViewHolder extends RecyclerView.ViewHolder {
        TextView tvAuthor, tvReplyTo, tvContent, tvTime;

        public SubReplyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvReplyTo = itemView.findViewById(R.id.tvReplyTo);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }

    public interface OnSubReplyClickListener {
        void onSubReplyClick(Reply reply);
    }
}


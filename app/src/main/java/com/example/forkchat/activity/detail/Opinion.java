package com.example.forkchat.activity.detail;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * 观点模型 - 对应需求中的"补充"或"质疑"观点
 * 支持二级短评
 */
public class Opinion implements Parcelable {

    public enum Type {
        SUPPLEMENT,  // 补充
        QUESTION     // 质疑
    }

    private final String id;
    private final Type type;
    private final String authorName;
    private final String authorAvatar;
    private final String publishTime;
    private final String content;
    private final int imageRes;
    private int likeCount;
    private int replyCount;
    private boolean isLiked;
    private boolean isExpanded;
    private final List<Reply> replies;

    public Opinion(String id, Type type, String authorName, String authorAvatar,
                   String publishTime, String content, int imageRes,
                   int likeCount, int replyCount, boolean isLiked) {
        this.id = id;
        this.type = type;
        this.authorName = authorName;
        this.authorAvatar = authorAvatar;
        this.publishTime = publishTime;
        this.content = content;
        this.imageRes = imageRes;
        this.likeCount = likeCount;
        this.replyCount = replyCount;
        this.isLiked = isLiked;
        this.isExpanded = false;
        this.replies = new ArrayList<>();
    }

    protected Opinion(Parcel in) {
        id = in.readString();
        type = Type.valueOf(in.readString());
        authorName = in.readString();
        authorAvatar = in.readString();
        publishTime = in.readString();
        content = in.readString();
        imageRes = in.readInt();
        likeCount = in.readInt();
        replyCount = in.readInt();
        isLiked = in.readByte() != 0;
        isExpanded = in.readByte() != 0;
        replies = new ArrayList<>();
        in.readTypedList(replies, Reply.CREATOR);
    }

    public static final Creator<Opinion> CREATOR = new Creator<Opinion>() {
        @Override
        public Opinion createFromParcel(Parcel in) {
            return new Opinion(in);
        }

        @Override
        public Opinion[] newArray(int size) {
            return new Opinion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(type.name());
        dest.writeString(authorName);
        dest.writeString(authorAvatar);
        dest.writeString(publishTime);
        dest.writeString(content);
        dest.writeInt(imageRes);
        dest.writeInt(likeCount);
        dest.writeInt(replyCount);
        dest.writeByte((byte) (isLiked ? 1 : 0));
        dest.writeByte((byte) (isExpanded ? 1 : 0));
        dest.writeTypedList(replies);
    }

    // Getters and setters
    public String getId() { return id; }
    public Type getType() { return type; }
    public String getAuthorName() { return authorName; }
    public String getAuthorAvatar() { return authorAvatar; }
    public String getPublishTime() { return publishTime; }
    public String getContent() { return content; }
    public int getImageRes() { return imageRes; }
    public int getLikeCount() { return likeCount; }
    public void setLikeCount(int likeCount) { this.likeCount = likeCount; }
    public int getReplyCount() { return replyCount; }
    public void setReplyCount(int replyCount) { this.replyCount = replyCount; }
    public boolean isLiked() { return isLiked; }
    public void setLiked(boolean liked) { isLiked = liked; }
    public boolean isExpanded() { return isExpanded; }
    public void setExpanded(boolean expanded) { isExpanded = expanded; }
    public List<Reply> getReplies() { return replies; }
    public void addReply(Reply reply) { 
        replies.add(0, reply);
        replyCount++;
    }
}

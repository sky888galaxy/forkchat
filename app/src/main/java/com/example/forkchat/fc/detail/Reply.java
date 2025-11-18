package com.example.forkchat.fc.detail;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 二级短评模型 - 对应需求中的观点下的短评回复
 */
public class Reply implements Parcelable {

    public enum Type {
        SUPPLEMENT("补充"),
        QUESTION("质疑");

        private final String label;
        Type(String label) { this.label = label; }
        public String getLabel() { return label; }
    }

    private final String id;
    private final String authorName;
    private final String authorAvatar;
    private final String publishTime;
    private final String content;
    private int likeCount;
    private boolean isLiked;
    private final Type type;

    public Reply(String id, String authorName, String authorAvatar,
                 String publishTime, String content, int likeCount, boolean isLiked, Type type) {
        this.id = id;
        this.authorName = authorName;
        this.authorAvatar = authorAvatar;
        this.publishTime = publishTime;
        this.content = content;
        this.likeCount = likeCount;
        this.isLiked = isLiked;
        this.type = type;
    }

    protected Reply(Parcel in) {
        id = in.readString();
        authorName = in.readString();
        authorAvatar = in.readString();
        publishTime = in.readString();
        content = in.readString();
        likeCount = in.readInt();
        isLiked = in.readByte() != 0;
        type = Type.valueOf(in.readString());
    }

    public static final Creator<Reply> CREATOR = new Creator<Reply>() {
        @Override
        public Reply createFromParcel(Parcel in) {
            return new Reply(in);
        }

        @Override
        public Reply[] newArray(int size) {
            return new Reply[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(authorName);
        dest.writeString(authorAvatar);
        dest.writeString(publishTime);
        dest.writeString(content);
        dest.writeInt(likeCount);
        dest.writeByte((byte) (isLiked ? 1 : 0));
        dest.writeString(type.name());
    }

    // Getters and setters
    public String getId() { return id; }
    public String getAuthorName() { return authorName; }
    public String getAuthorAvatar() { return authorAvatar; }
    public String getPublishTime() { return publishTime; }
    public String getContent() { return content; }
    public int getLikeCount() { return likeCount; }
    public void setLikeCount(int likeCount) { this.likeCount = likeCount; }
    public boolean isLiked() { return isLiked; }
    public void setLiked(boolean liked) { isLiked = liked; }
    public Type getType() { return type; }
}

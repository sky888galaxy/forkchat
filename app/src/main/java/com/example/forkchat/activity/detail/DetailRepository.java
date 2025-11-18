package com.example.forkchat.activity.detail;

import com.example.forkchat.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 详情模块数据仓库 - 对应需求中的话题详情数据结构
 */
public final class DetailRepository {

    private DetailRepository() {
        // Utility class.
    }

    public static Post loadFeaturedPost() {
        return new Post(
                "topic_001",
                "如何在校园社区中促进理性讨论？",
                "最近在观察各类校园社区平台时，发现很多讨论容易陷入情绪化争吵，缺乏建设性交流。"
                        + "我认为可以尝试引入\"补充-质疑\"双列结构，引导用户从不同角度理性表达观点。\n\n"
                        + "具体设想：当一个话题发布后，用户可以选择在\"补充\"列提供支持性论据或扩展思路，"
                        + "也可以在\"质疑\"列提出反对意见或质疑点。这样可以避免单一评论区的混乱，"
                        + "让不同立场的声音都有展示空间。大家觉得这个方案可行吗？",
                "李明",
                "avatar_1",
                "2小时前",
                Arrays.asList("讨论", "社区", "产品设计"),
                R.drawable.detail_placeholder,
                328,
                156,
                12,
                8,
                false,
                false
        );
    }

    public static ArrayList<Opinion> loadSupplementOpinions() {
        ArrayList<Opinion> opinions = new ArrayList<>();
        
        Opinion op1 = new Opinion(
                "sup_001",
                Opinion.Type.SUPPLEMENT,
                "张华",
                "avatar_2",
                "1小时前",
                "非常赞同这个想法！我在Reddit看到过类似的实现，通过upvote/downvote机制配合双列展示，"
                        + "确实能让讨论更有序。建议可以加入热度排序，让高质量补充和质疑更容易被看到。",
                0,
                89,
                5,
                false
        );
        op1.getReplies().add(new Reply("rep_001", "李明", "avatar_1", "50分钟前",
                "对，热度排序很重要！这样可以避免好的观点被淹没。", 12, false, Reply.Type.SUPPLEMENT));
        op1.getReplies().add(new Reply("rep_002", "王芳", "avatar_3", "45分钟前",
                "但热度排序会不会导致\"羊群效应\"？先发的观点容易获得更多点赞。", 8, false, Reply.Type.QUESTION));
        opinions.add(op1);

        opinions.add(new Opinion(
                "sup_002",
                Opinion.Type.SUPPLEMENT,
                "刘洋",
                "avatar_4",
                "45分钟前",
                "补充一个技术实现方案：可以用ViewPager2 + TabLayout实现左右滑动切换补充/质疑列表，"
                        + "每列用RecyclerView展示观点，支持无限加载。",
                0,
                67,
                2,
                false
        ));

        opinions.add(new Opinion(
                "sup_003",
                Opinion.Type.SUPPLEMENT,
                "陈静",
                "avatar_5",
                "30分钟前",
                "这个设计在心理学上也有依据。当用户明确知道自己在\"补充\"还是\"质疑\"，"
                        + "会更容易保持理性态度，而不是无脑站队或互喷。",
                0,
                54,
                1,
                false
        ));

        return opinions;
    }

    public static ArrayList<Opinion> loadQuestionOpinions() {
        ArrayList<Opinion> opinions = new ArrayList<>();

        Opinion op1 = new Opinion(
                "ques_001",
                Opinion.Type.QUESTION,
                "赵强",
                "avatar_6",
                "1小时前",
                "我担心这种设计会让讨论过于割裂。补充列和质疑列的用户可能各说各话，"
                        + "缺乏正面交锋和深度互动。最后可能变成两个平行的\"回音室\"。",
                0,
                103,
                4,
                false
        );
        op1.getReplies().add(new Reply("rep_003", "李明", "avatar_1", "40分钟前",
                "好问题！可以考虑增加\"关联引用\"功能，让补充和质疑可以互相引用回复。", 15, false, Reply.Type.SUPPLEMENT));
        opinions.add(op1);

        opinions.add(new Opinion(
                "ques_002",
                Opinion.Type.QUESTION,
                "孙丽",
                "avatar_7",
                "50分钟前",
                "用户真的会认真选择在哪一列发言吗？我觉得大部分人不会关注这个分类，"
                        + "最后可能还是随便发，反而增加了操作复杂度。",
                0,
                76,
                3,
                false
        ));

        opinions.add(new Opinion(
                "ques_003",
                Opinion.Type.QUESTION,
                "周杰",
                "avatar_8",
                "25分钟前",
                "如果一个观点既有补充又有质疑的成分怎么办？强行分类可能会限制表达的完整性。",
                0,
                61,
                2,
                false
        ));

        return opinions;
    }

    public static Opinion createUserOpinion(Opinion.Type type, String content) {
        return new Opinion(
                "user_op_" + System.currentTimeMillis(),
                type,
                "当前用户",
                "avatar_me",
                "刚刚",
                content,
                0,
                0,
                0,
                false
        );
    }

    public static Reply createUserReply(String content, Reply.Type type) {
        return new Reply(
                "user_rep_" + System.currentTimeMillis(),
                "当前用户",
                "avatar_me",
                "刚刚",
                content,
                0,
                false,
                type
        );
    }
}

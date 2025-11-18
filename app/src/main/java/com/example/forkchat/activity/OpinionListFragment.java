package com.example.forkchat.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forkchat.R;
import com.example.forkchat.activity.detail.DetailRepository;
import com.example.forkchat.activity.detail.Opinion;
import com.example.forkchat.activity.detail.OpinionAdapter;
import com.example.forkchat.activity.detail.Reply;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

/**
 * 观点列表Fragment - 展示补充或质疑列表
 */
public class OpinionListFragment extends Fragment implements OpinionAdapter.OnOpinionListener {

    private static final String ARG_OPINIONS = "opinions";
    private static final String ARG_TYPE = "type";

    private RecyclerView recyclerView;
    private OpinionAdapter adapter;
    private ArrayList<Opinion> opinions;
    private Opinion.Type type;

    public static OpinionListFragment newInstance(ArrayList<Opinion> opinions, Opinion.Type type) {
        OpinionListFragment fragment = new OpinionListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_OPINIONS, opinions);
        args.putString(ARG_TYPE, type.name());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            opinions = getArguments().getParcelableArrayList(ARG_OPINIONS);
            type = Opinion.Type.valueOf(getArguments().getString(ARG_TYPE));
        }
        if (opinions == null) {
            opinions = new ArrayList<>();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        recyclerView = new RecyclerView(requireContext());
        recyclerView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        return recyclerView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        adapter = new OpinionAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);
        adapter.submitList(opinions);
    }

    public void addOpinion(Opinion opinion) {
        opinions.add(0, opinion);
        adapter.addOpinion(opinion);
        recyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void onLikeClick(Opinion opinion) {
        opinion.setLiked(!opinion.isLiked());
        if (opinion.isLiked()) {
            opinion.setLikeCount(opinion.getLikeCount() + 1);
            showSnackbar(R.string.toast_liked);
        } else {
            opinion.setLikeCount(opinion.getLikeCount() - 1);
            showSnackbar(R.string.toast_unliked);
        }
        adapter.updateOpinion(opinion);
    }

    @Override
    public void onReplyClick(Opinion opinion) {
        showReplyDialog(opinion);
    }

    @Override
    public void onExpandClick(Opinion opinion) {
        opinion.setExpanded(!opinion.isExpanded());
        adapter.updateOpinion(opinion);
    }

    @Override
    public void onDeriveClick(Opinion opinion) {
        showSnackbar(R.string.toast_derive_todo);
    }

    private void showReplyDialog(Opinion opinion) {
        EditText editText = new EditText(requireContext());
        editText.setHint(R.string.hint_add_reply);
        editText.setMaxLines(3);
        
        new AlertDialog.Builder(requireContext())
                .setTitle("回复短评")
                .setView(editText)
                .setPositiveButton(R.string.action_submit, (dialog, which) -> {
                    String content = editText.getText().toString().trim();
                    if (content.isEmpty()) {
                        showSnackbar(R.string.toast_reply_empty);
                        return;
                    }
                    if (content.length() > 200) {
                        showSnackbar(R.string.toast_reply_too_long);
                        return;
                    }
                    
                    Reply reply = DetailRepository.createUserReply(content, Reply.Type.SUPPLEMENT);
                    opinion.addReply(reply);
                    opinion.setExpanded(true);
                    adapter.updateOpinion(opinion);
                    showSnackbar(R.string.toast_reply_added);
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    private void showSnackbar(int messageRes) {
        if (getView() != null) {
            Snackbar.make(getView(), messageRes, Snackbar.LENGTH_SHORT).show();
        }
    }
}

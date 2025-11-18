package com.example.forkchat.activity.detail;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Adds consistent spacing between vertically stacked RecyclerView items.
 */
public class SpacingItemDecoration extends RecyclerView.ItemDecoration {

    private final int verticalSpace;

    public SpacingItemDecoration(int verticalSpace) {
        this.verticalSpace = verticalSpace;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        if (position == RecyclerView.NO_POSITION) {
            return;
        }
        outRect.top = position == 0 ? 0 : verticalSpace;
        outRect.bottom = 0;
    }
}

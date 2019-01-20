package com.example.dantczak.got.Utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.dantczak.got.R;

public class RecycleViewDividerDecorator extends RecyclerView.ItemDecoration
{
    private Drawable divider;

    public RecycleViewDividerDecorator(Context context)
    {
        divider = context.getResources().getDrawable(R.drawable.horizontal_line);
    }

    @Override
    public void onDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state)
    {
        if(parent.getChildCount() == 0)
        {
            return;
        }

        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        View child = parent.getChildAt(0);

        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

        int top = child.getTop() + params.topMargin;
        int bottom = top + divider.getIntrinsicHeight();

        divider.setBounds(left, top, right, bottom);
        divider.draw(canvas);

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            child = parent.getChildAt(i);

            params = (RecyclerView.LayoutParams) child.getLayoutParams();

            top = child.getBottom() + params.bottomMargin;
            bottom = top + divider.getIntrinsicHeight();

            divider.setBounds(left, top, right, bottom);
            divider.draw(canvas);
        }
    }
}
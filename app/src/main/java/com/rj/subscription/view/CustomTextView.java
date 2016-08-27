package com.rj.subscription.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by lijunyan on 2016/8/23.
 */
public class CustomTextView extends TextView {

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.rotate(45, getMeasuredWidth()/2, getMeasuredHeight()/2);
        super.onDraw(canvas);
    }

}
package com.rj.subscription.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

/**
 * Created by lijunyan on 2016/8/23.
 */
public class MyGridView extends GridView {
    public MyGridView(Context context) {
        super(context);
    }
    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public MyGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    protected void dispatchDraw(Canvas canvas){
        super.dispatchDraw(canvas);
        int column = getNumColumns();
        int childCount = getChildCount();
        Paint localPaint;
        localPaint = new Paint();
        localPaint.setStyle(Paint.Style.STROKE);
        localPaint.setColor(Color.parseColor("#E5E5E5"));
        localPaint.setStrokeWidth(3);
        for(int i = 0;i < childCount;i++){
            View cellView = getChildAt(i);
            if((i + 1) % column == 0){
                //最右一列cellView画下边的线
                canvas.drawLine(cellView.getLeft(), cellView.getBottom(), cellView.getRight(), cellView.getBottom(), localPaint);

            }else if((i + 1) > (childCount - (childCount % column))){
                //如果是最后一排则画下边的线
                canvas.drawLine(cellView.getLeft(), cellView.getBottom(), cellView.getRight(), cellView.getBottom(), localPaint);
                canvas.drawLine(cellView.getRight(), cellView.getTop(), cellView.getRight(), cellView.getBottom(), localPaint);
            }
            else{
                //其他所有画右边和下边两条线
                canvas.drawLine(cellView.getLeft(), cellView.getBottom(), cellView.getRight(), cellView.getBottom(), localPaint);
                canvas.drawLine(cellView.getRight(), cellView.getTop(), cellView.getRight(), cellView.getBottom(), localPaint);
            }
        }

        //为最后一行没有满上的child的空格子画上边界线
        if(childCount % column != 0){
            for(int j = 0 ;j < (column-childCount % column) ; j++){
                View lastView = getChildAt(childCount - 1);
                //    canvas.drawLine(lastView.getRight() + lastView.getWidth() * j, lastView.getTop(), lastView.getRight() + lastView.getWidth()* j, lastView.getBottom(), localPaint);
            }
        }
    }
}
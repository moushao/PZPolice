package com.pvirtech.pzpolice.third.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by Administrator on 2017/4/10.
 * 自定义阶段的进度条
 */

public class StageView extends View {
    int mCx, mCy;// x、y轴中心点
    int mCxUnit, mCyUnit;// x、y分为10分
    private int mWidth, mHeight;// 宽高
    Paint paint = new Paint();
    int strokeWidth = 3;
    int color = 0xFF7783AD;
    /**
     * 1:没有上面的线条；2，没有下面的线条；3：上下都有线条；4：上下没线条
     */
    int state;

    public StageView(Context context) {
        super(context);
    }

    public StageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mCx = mWidth / 2;
        mCy = mHeight / 2;
        mCxUnit = (mCy - strokeWidth) / 20;
        mCyUnit = (mCy - strokeWidth) / 20;
//        strokeWidth = mCxUnit;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        canvas.translate(mCx, mCy);
        canvas.drawCircle(0, 0, 15, paint);

        paint.setStrokeWidth(strokeWidth * 1);
        if (state == 1) {
            canvas.drawLine(0, 0, 0, mCy, paint);
        } else if (state == 2) {
            canvas.drawLine(0, 0, 0, -mCy, paint);
        } else if (state == 3) {
            canvas.drawLine(0, -mCy, 0, mCy, paint);
        } else if (state == 4) {
        }
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        paint.setColor(color);
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void invalidateView() {
        this.invalidate();
    }
}

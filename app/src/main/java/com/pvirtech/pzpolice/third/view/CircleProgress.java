package com.pvirtech.pzpolice.third.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/29.
 * 自定义view
 * 用于显示任务的进度和大体概览
 */

public class CircleProgress extends View {
    private Context mContext;
    int mCx, mCy;// x、y轴中心点
    int mCxUnit, mCyUnit;// x、y分为10分
    private int mWidth, mHeight;// 宽高
    Paint paint = new Paint();
    int strokeWidth;
    int imageView; // 执行扫描运动的图片
    Bitmap centerBitmap;
    List<CircleProgressProgress> list = new ArrayList<>();
    private String strContent;


    public CircleProgress(Context context) {
        super(context);
        mContext = context;
    }

    public CircleProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public CircleProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mCx = mWidth / 2;
        mCy = mHeight / 2;
        mCxUnit = (mCy - strokeWidth) / 10;
        mCyUnit = (mCy - strokeWidth) / 10;
        strokeWidth = (int) (mCxUnit * 1.5);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setAntiAlias(true);
        String strProPressContent = "";
        paint.setStyle(Paint.Style.STROKE);

        paint.setStrokeWidth(strokeWidth);
        canvas.translate(mCx, mCy);
        RectF rectF = new RectF(strokeWidth - mCx, strokeWidth - mCy, mCx - strokeWidth, mCy - strokeWidth);
        int intTotal = 0;
        int intTotalProgress = -90;

        for (CircleProgressProgress circleProgressProgress : list) {
            intTotal += circleProgressProgress.getProgress();
        }

        /**
         * 画圆环
         */
        for (CircleProgressProgress circleProgressProgress : list) {
            paint.setColor(circleProgressProgress.getColor());
            int currentProgress = 360;
            if (intTotal == 0) {
                currentProgress = 360;
            } else {
                currentProgress = 360 * circleProgressProgress.getProgress() / intTotal;
            }
            canvas.drawArc(rectF, intTotalProgress, currentProgress, false, paint);
            intTotalProgress += currentProgress;
            if (intTotal == 0) {
                break;
            }
        }

        /**
         * 统计每个的个数
         */
        int total = 0;
        for (int i = 0; i <= list.size() - 1; i++) {
            strProPressContent += (list.get(i).getProgress() + total) + "/";
            total += list.get(i).getProgress();
        }


        if (imageView != 0) {
            /**
             * 画中间的图标
             */
            centerBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(mContext.getResources(), getImageView()), mCxUnit * 4, mCxUnit *
                    4, false);
            if (null != centerBitmap) {
                int imageViewStartPosition = -6 * mCxUnit;
                canvas.drawBitmap(centerBitmap, -centerBitmap.getWidth() / 2, imageViewStartPosition, null);

                if (strProPressContent.endsWith("/")) {
                    /**
                     * 画各个实体的个数
                     */
                    strProPressContent = strProPressContent.substring(0, strProPressContent.length() - 1);
                    int textViewStartPosition = imageViewStartPosition + 2 * mCyUnit + centerBitmap.getHeight() + mCxUnit;
                    paint.setTextSize(3 * mCxUnit);
                    paint.setColor(0XFF626161);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setTextAlign(Paint.Align.CENTER);
                    paint.setTypeface(Typeface.DEFAULT_BOLD);
                    canvas.drawText(strProPressContent, 0, textViewStartPosition, paint);

                    if (!TextUtils.isEmpty(strContent)) {
                        /**
                         * 画中间的提示文字
                         */
                        textViewStartPosition += 3 * mCxUnit + mCxUnit;
                        canvas.drawText(getStrContent(), 0, textViewStartPosition, paint);
                    }
                }
                if (centerBitmap != null && !centerBitmap.isRecycled()) {
                    centerBitmap.recycle();
                    centerBitmap = null;
                }
            }
        }
    }


    public List<CircleProgressProgress> getList() {
        return list;
    }

    public void setList(List<CircleProgressProgress> list) {
        this.list.clear();
        this.list = list;
    }

    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }

    public void invalidateView() {
        this.invalidate();
    }

    public String getStrContent() {
        return strContent;
    }

    public void setStrContent(String strContent) {
        this.strContent = strContent;
    }
}

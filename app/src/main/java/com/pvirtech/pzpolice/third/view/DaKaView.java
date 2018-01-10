package com.pvirtech.pzpolice.third.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.pvirtech.pzpolice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/10.
 */
public class DaKaView extends View {
    private Context mContext;
    private boolean isSearching = false;// 标识是否处于扫描状态,默认为不在扫描状态
    private Paint mPaint;// 画笔
    private Bitmap mScanBmp;// 执行扫描运动的图片
    private Bitmap mScanBmp2;// 执行扫描运动的图片
    private int mOffsetArgs = 0;// 扫描运动偏移量参数
    private List<String> mPointArray = new ArrayList<String>();// 存放偏移量的map
    private int mWidth, mHeight;// 宽高
    int mOutWidth;// 外圆宽度(w/4/5*2=w/10)
    int mCx, mCy;// x、y轴中心点
    int mOutsideRadius, mInsideRadius;// 外、内圆半径
    private int intStrokeWidth = 3;

    public DaKaView(Context context) {
        super(context);
    }

    public void init(Context context) {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(intStrokeWidth);
        this.mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取控件区域宽高
        if (mWidth == 0 || mHeight == 0) {
            final int minimumWidth = getSuggestedMinimumWidth();
            final int minimumHeight = getSuggestedMinimumHeight();
            mWidth = resolveMeasured(widthMeasureSpec, minimumWidth);
            mHeight = resolveMeasured(heightMeasureSpec, minimumHeight);
            mScanBmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.radar_scan_img), mWidth -
                    mOutWidth, mWidth - mOutWidth, false);
            mScanBmp2 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.daka), mWidth - mOutWidth, mWidth
                    - mOutWidth, false);


            // 获取x/y轴中心点
            mCx = mWidth / 2;
            mCy = mHeight / 2;

            // 获取外圆宽度
            mOutWidth = mWidth / 10;

            // 计算内、外半径
            mOutsideRadius = mWidth / 2;// 外圆的半径
            mInsideRadius = (mWidth - mOutWidth) / 4 / 2;// 内圆的半径,除最外层,其它圆的半径=层数*insideRadius
        }
    }

    public DaKaView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 3.绘制扫描扇形图
        canvas.save();// 用来保存Canvas的状态.save之后，可以调用Canvas的平移、放缩、旋转、错切、裁剪等操作.

        if (isSearching) {// 判断是否处于扫描
            canvas.rotate(mOffsetArgs, mCx, mCy);// 绘制旋转角度,参数一：角度;参数二：x中心;参数三：y中心.
            canvas.drawBitmap(mScanBmp, mCx - mScanBmp.getWidth() / 2, mCy - mScanBmp.getHeight() / 2, null);// 绘制Bitmap扫描图片效果
            mOffsetArgs += 3;
        } else {
            canvas.drawBitmap(mScanBmp2, mCx - mScanBmp.getWidth() / 2, mCy - mScanBmp.getHeight() / 2, null);
        }
//        canvas.drawCircle(mCx, mCx, (mWidth - mOutWidth)/2-intStrokeWidth, mPaint);
        if (isSearching) this.invalidate();

    }


    /**
     * TODO<解析获取控件宽高>
     *
     * @return int
     */
    private int resolveMeasured(int measureSpec, int desired) {
        int result = 0;
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (MeasureSpec.getMode(measureSpec)) {
            case MeasureSpec.UNSPECIFIED:
                result = desired;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(specSize, desired);
                break;
            case MeasureSpec.EXACTLY:
            default:
                result = specSize;
        }
        return result;
    }

    /**
     * TODO<设置扫描状态>
     *
     * @return void
     */
    public void setSearching(boolean status) {
        this.isSearching = status;
        this.invalidate();
    }


}

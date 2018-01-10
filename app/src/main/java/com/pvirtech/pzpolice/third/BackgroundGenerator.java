package com.pvirtech.pzpolice.third;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;

/*********************************************************************************
 * Project Name  : MetroCompanyOneClient
 * Package       : org.metroclient.utils.drawable
 * <p/>
 * <p/>
 * Copyright  成都杰迈科技  Corporation 2016 All Rights Reserved
 * <p/>
 * <p/>
 * <Pre>
 * TODO  背景（选择器）生成器
 * </Pre>
 * <pr>
 * state Mapping
 * **************************************
 * |   _normal        |  （default state）
 * |   _pressed       |   state_pressed      是否按下，如一个按钮触摸或者点击
 * |   _focused       |   state_focused      是否取得焦点，比如用户选择了一个文本框
 * |   _disabled      |   state_disabled
 * |   _checked       |   state_checked      被checked了，如：一个RadioButton可以被check了。
 * |   _selected      |   state_selected     它与focus state并不完全一样，如一个list view 被选中的时候，它里面的各个子组件可能通过方向键，被选中了。
 * |   _hovered       |   state_hovered      光标是否悬停，通常与focused state相同，它是4.0的新特性
 * |   _checkable     |   state_checkable    组件是否能被check。如：RadioButton是可以被check的
 * |   _activated     |   state_activated
 * |   _windowfocused |   state_windowfocused  应用程序是否在前台，当有通知栏被拉下来或者一个对话框弹出的时候应用程序就不在前台了
 * <p/>
 * <p/>
 * <p/>
 * </pr>
 *
 * @AUTHOR by Justice
 * Created by 2016/3/24 0024 上午 11:47.
 * <p/>
 * <p/>
 * ********************************************************************************
 */
public class BackgroundGenerator {


    /**
     * 获取shape背景Drawable
     *
     * @param context
     * @param fillColorResId
     * @param roundRadius
     * @param strokeWidth
     * @param strokeColorResId
     * @return 但是如果想设置Gradient的渐变色该咋办呢？
     * 方法是改变GradientDrawable的创建方法：
     * int colors[] = { 0xff255779 , 0xff3e7492, 0xffa6c0cd };//分别为开始颜色，中间夜色，结束颜色
     * GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
     */
    public static GradientDrawable generateSingleBackground(Context context, int fillColorResId, int roundRadius, int strokeWidth, int strokeColorResId) {
        // prepare
//        int strokeColor = Color.parseColor("#2E3135");
        int strokeColor = context.getResources().getColor(strokeColorResId);
        int fillColor = context.getResources().getColor(fillColorResId);
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(fillColor);
        gd.setCornerRadius(dip2px(context, roundRadius));
        gd.setStroke(dip2px(context, strokeWidth), strokeColor);
        return gd;
    }

    /**
     * 获取圆形背景
     * @param context
     * @param fillColorResId
     * @param strokeWidth
     * @param strokeColorResId
     * @return
     */
    public static GradientDrawable generateSingleCycleBackground(Context context, int fillColorResId, int strokeWidth, int strokeColorResId) {
        // prepare
//        int strokeColor = Color.parseColor("#2E3135");
        int strokeColor = context.getResources().getColor(strokeColorResId);
        int fillColor = context.getResources().getColor(fillColorResId);
        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.OVAL);
        gd.setUseLevel(false);
        gd.setColor(fillColor);
        gd.setStroke(dip2px(context, strokeWidth), strokeColor);
        return gd;
    }

    /**
     * 获取指定外框颜色宽度以及填充色的背景选择器
     * @param context
     * @param normalFillColorResId
     * @param pressedFillColorResId
     * @param strokeWidth
     * @param strokeColorResId
     * @return
     */
    public static StateListDrawable getCyclePressedDrawableStateList(Context context, int normalFillColorResId, int pressedFillColorResId, int strokeWidth, int strokeColorResId) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_focused}, generateSingleCycleBackground(context, pressedFillColorResId, strokeWidth, strokeColorResId));
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, generateSingleCycleBackground(context, pressedFillColorResId, strokeWidth, strokeColorResId));
        stateListDrawable.addState(new int[]{}, generateSingleCycleBackground(context, normalFillColorResId, strokeWidth, strokeColorResId));
        return stateListDrawable;
    }


    /**
     * 获取CheckBox,RadioButton等控件的图片背景选择器对象
     *
     * @param normalDrawable
     * @param checkedDrawable
     * @param context
     * @return
     */
    public static StateListDrawable getCheckedDrawableStateList(int normalDrawable, int checkedDrawable, Context context) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_checked}, context.getResources().getDrawable(checkedDrawable));
        stateListDrawable.addState(new int[]{android.R.attr.state_selected}, context.getResources().getDrawable(checkedDrawable));
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, context.getResources().getDrawable(checkedDrawable));
        stateListDrawable.addState(new int[]{}, context.getResources().getDrawable(normalDrawable));
        return stateListDrawable;
    }

    public static final int DIRECTION_AROUND = 0;//四角圆角
    public static final int DIRECTION_LEFT = 1;//左边圆角
    public static final int DIRECTION_TOP = 2;//上边圆角
    public static final int DIRECTION_RIGHT = 3; //右边圆角
    public static final int DIRECTION_BOTTOM = 4;//下边圆角

    /**
     * 获取制定圆角、方向和颜色的背景选择器（适用与checkBox和RadioButton）
     *
     * @param normalColor
     * @param checkedColor
     * @param radius
     * @param direction
     * @param context
     * @return
     */
    public static StateListDrawable getCheckedShapeColorStateList(int normalColor, int checkedColor, int radius, int direction, Context context) {
        switch (direction) {
            case DIRECTION_AROUND:
                return getCheckedShapeColorStateList(normalColor, checkedColor, radius, radius, radius, radius, context);
            case DIRECTION_LEFT:
                return getCheckedShapeColorStateList(normalColor, checkedColor, radius, 0, 0, radius, context);
            case DIRECTION_TOP:
                return getCheckedShapeColorStateList(normalColor, checkedColor, radius, radius, 0, 0, context);
            case DIRECTION_RIGHT:
                return getCheckedShapeColorStateList(normalColor, checkedColor, 0, radius, radius, 0, context);
            case DIRECTION_BOTTOM:
                return getCheckedShapeColorStateList(normalColor, checkedColor, 0, 0, radius, radius, context);
            default:
                return getCheckedShapeColorStateList(normalColor, checkedColor, radius, radius, radius, radius, context);
        }
    }

    /**
     * 获取制定颜色和圆角的shape背景选择器
     *
     * @param normalColor
     * @param checkedColor
     * @param leftTopRadius
     * @param rightTopRadius
     * @param rightBottomRadius
     * @param leftBottomRadius
     * @param context
     * @return
     */
    private static StateListDrawable getCheckedShapeColorStateList(int normalColor, int checkedColor, int leftTopRadius, int rightTopRadius, int rightBottomRadius, int leftBottomRadius, Context context) {
        StateListDrawable stateListDrawable = new StateListDrawable();

        float[] outerRoundedCorners = getRoundedCorner(context, leftTopRadius, rightTopRadius, rightBottomRadius, leftBottomRadius);
        float[] innerRoundedCorners = getRoundedCorner(context, 0, 0, 0, 0);
        RoundRectShape roundedShape = new RoundRectShape(outerRoundedCorners, null, innerRoundedCorners);

        ShapeDrawable checkedDrawable = new ShapeDrawable(roundedShape);
        checkedDrawable.getPaint().setColor(context.getResources().getColor(checkedColor));
//        checkedDrawable.setPadding(20, 20, 20, 20);
        stateListDrawable.addState(new int[]{android.R.attr.state_checked}, checkedDrawable);
        stateListDrawable.addState(new int[]{android.R.attr.state_selected}, checkedDrawable);
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, checkedDrawable);

        ShapeDrawable normalDrawable = new ShapeDrawable(roundedShape);
        normalDrawable.getPaint().setColor(context.getResources().getColor(normalColor));
        stateListDrawable.addState(new int[]{}, normalDrawable);

        return stateListDrawable;
    }

    private static float[] getRoundedCorner(Context context, int leftTopRadius, int rightTopRadius, int rightBottomRadius, int leftBottomRadius) {
        float[] roundedCorners = new float[8];
        roundedCorners[0] = dip2px(context, leftTopRadius);
        roundedCorners[1] = dip2px(context, leftTopRadius);
        roundedCorners[2] = dip2px(context, rightTopRadius);
        roundedCorners[3] = dip2px(context, rightTopRadius);
        roundedCorners[4] = dip2px(context, rightBottomRadius);
        roundedCorners[5] = dip2px(context, rightBottomRadius);
        roundedCorners[6] = dip2px(context, leftBottomRadius);
        roundedCorners[7] = dip2px(context, leftBottomRadius);
        return roundedCorners;
    }


    /**
     * 获取背景图片选择器
     *
     * @param normalDrawable
     * @param pressedDrawable
     * @param context
     * @return
     */
    public static StateListDrawable getPressedImageStateList(int normalDrawable, int pressedDrawable, Context context) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, context.getResources().getDrawable(pressedDrawable));
        stateListDrawable.addState(new int[]{android.R.attr.state_focused}, context.getResources().getDrawable(pressedDrawable));
        stateListDrawable.addState(new int[]{}, context.getResources().getDrawable(normalDrawable));
        return stateListDrawable;
    }


    /**
     * 获取Shape背景选择器
     *
     * @param normalColor        普通色
     * @param pressedColor       按下色
     * @param outerRoundedCorner 外部圆角
     * @param innerRoundedCorner 内部圆角
     * @param context
     * @return
     */
    public static StateListDrawable getPressedShapeColorStateList(int normalColor, int pressedColor, float outerRoundedCorner, float innerRoundedCorner, Context context) {
        StateListDrawable stateListDrawable = new StateListDrawable();

//        float[] roundedCorner = new float[]{4, 4, 4, 4, 4, 4, 4, 4};
//        float[] innerRoundedCorner = new float[]{0, 0, 0, 0, 0, 0, 0, 0};
        float[] outerRoundedCorners = getRoundedCorner(context, outerRoundedCorner);
        float[] innerRoundedCorners = getRoundedCorner(context, innerRoundedCorner);
        RoundRectShape roundedShape = new RoundRectShape(outerRoundedCorners, null, innerRoundedCorners);

        ShapeDrawable pressedDrawable = new ShapeDrawable(roundedShape);
        pressedDrawable.getPaint().setColor(context.getResources().getColor(pressedColor));
//        pressedDrawable.setPadding(20, 20, 20, 20);
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);

        ShapeDrawable normalDrawable = new ShapeDrawable(roundedShape);
        normalDrawable.getPaint().setColor(context.getResources().getColor(normalColor));
        stateListDrawable.addState(new int[]{}, normalDrawable);

        return stateListDrawable;
    }

    private static float[] getRoundedCorner(Context context, float roundedCorner) {
        float[] roundedCorners = new float[8];
        for (int i = 0; i < 8; i++) {
            roundedCorners[i] = dip2px(context, roundedCorner);
        }
        return roundedCorners;

    }

    /**
     * dip转换成px
     *
     * @param context
     * @param dipValue
     * @return
     */
    private static int dip2px(Context context, float dipValue) {
        // 获取屏幕的密度
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
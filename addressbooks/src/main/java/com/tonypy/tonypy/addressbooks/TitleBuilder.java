package com.tonypy.tonypy.addressbooks;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.Arrays;
import com.tonypy.tonypy.addressbooks.R;
/**
 * Created by tonypy on 2017/04/10.
 */

public class TitleBuilder {



    private View titleview;
    private RoundedImageView titlebar_iv_left, titlebar_iv_right;
    private TextView titlebar_tv_left, titlebar_tv_right;
    private TextView titlebar_tv_center;



    public TitleBuilder(Activity context) {
        titleview = context.findViewById(R.id.rl_titlebar);
        titlebar_iv_left = (RoundedImageView) titleview.findViewById(R.id.titlebar_iv_left);
        titlebar_tv_left = (TextView) titleview.findViewById(R.id.titlebar_tv_left);

        titlebar_iv_right = (RoundedImageView) titleview.findViewById(R.id.titlebar_iv_right);
        titlebar_tv_right = (TextView) titleview.findViewById(R.id.titlebar_tv_right);
     
    }

    public TitleBuilder(View context) {
        titleview = context.findViewById(R.id.rl_titlebar);
        titlebar_iv_left = (RoundedImageView) titleview.findViewById(R.id.titlebar_iv_left);
        titlebar_tv_left = (TextView) titleview.findViewById(R.id.titlebar_tv_left);

        titlebar_iv_right = (RoundedImageView) titleview.findViewById(R.id.titlebar_iv_right);
        titlebar_tv_right = (TextView) titleview.findViewById(R.id.titlebar_tv_right);

    }

    public TitleBuilder setTableLayout(String[] tablist, final Fragment[] fragmentlist, FragmentManager fragmentManager) {

        return this;
    }

    public TitleBuilder seTitleBgRes(int resid) {
        titleview.setBackgroundResource(resid);
        return this;
    }

    public TitleBuilder setTitleText(CharSequence text) {
        titlebar_tv_center.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
        titlebar_tv_center.setText(text);
        return this;
    }

    public TitleBuilder setLeftImage(int resid) {
        titlebar_iv_left.setVisibility(resid > 0 ? View.VISIBLE : View.GONE);
        titlebar_iv_left.setImageResource(resid);
        return this;
    }

    public TitleBuilder setLeftText(String text) {
        titlebar_tv_left.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
        titlebar_tv_left.setText(text);
        return this;
    }

    public TitleBuilder setLeftOnClickListenner(View.OnClickListener onClickListenner) {
        if (titlebar_iv_left.getVisibility() == View.VISIBLE) {
            titlebar_iv_left.setOnClickListener(onClickListenner);
        } else if (titlebar_tv_left.getVisibility() == View.VISIBLE) {
            titlebar_tv_left.setOnClickListener(onClickListenner);
        }
        return this;
    }

    public TitleBuilder setRightImage(int resid) {
        titlebar_iv_right.setVisibility(resid > 0 ? View.VISIBLE : View.GONE);
        titlebar_iv_right.setImageResource(resid);
        return this;
    }

    public TitleBuilder setRightText(String text) {
        titlebar_tv_right.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
        titlebar_tv_right.setText(text);
        return this;
    }

    public TitleBuilder setRightOnClickListenner(View.OnClickListener onClickListenner) {
        if (titlebar_iv_right.getVisibility() == View.VISIBLE) {
            titlebar_iv_right.setOnClickListener(onClickListenner);
        } else if (titlebar_tv_right.getVisibility() == View.VISIBLE) {
            titlebar_tv_right.setOnClickListener(onClickListenner);
        }
        return this;
    }

    public View Builder() {
        return titleview;
    }
}

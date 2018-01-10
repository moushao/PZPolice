package com.pvirtech.pzpolice.utils;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pvirtech.pzpolice.R;


/**
 * Created by pd on 2016/10/20.
 */

public class LoadingViewProgress {
    /**
     * 加载数据对话框
     */
    private Dialog mLoadingDialog;


    /**
     * 显示加载对话框
     *
     * @param context    上下文
     * @param msg        对话框显示内容
     * @param cancelable 对话框是否可以取消
     */
    public void showDialogForLoading(Context context, String msg, boolean cancelable) {
        if (!getIsShowing()) {
            View view = LayoutInflater.from(context).inflate(R.layout.loading_view_progress, null);
            if (!TextUtils.isEmpty(msg)) {
                TextView loadingText = (TextView) view.findViewById(R.id.tv_loading);
                loadingText.setText(msg);
            }
            if (null == mLoadingDialog) {
                mLoadingDialog = new Dialog(context, R.style.loading_dialog_style);
                mLoadingDialog.setCanceledOnTouchOutside(cancelable);
                mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams
                        .MATCH_PARENT));
            }
            mLoadingDialog.show();
        }
    }

    /**
     * 关闭加载对话框
     */
    public void hideDialogForLoading() {
        if (getIsShowing()) {
            mLoadingDialog.cancel();
        }
    }

    public boolean getIsShowing() {
        if (null == mLoadingDialog) {
            return false;
        }
        if (mLoadingDialog.isShowing()) {
            return true;
        } else {
            return false;
        }
    }


}

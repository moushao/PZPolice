package com.pvirtech.pzpolice.ui.base;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pvirtech.pzpolice.R;


/**
 * Created by qfxl on 2016/3/24.
 */
public class BaseDialog extends  AlertDialog {
    private Context mContext;
    private View myContentView;
    private String title;
    private OnBaseDialogClickListener positiviOnclickListener;
    private OnBaseDialogClickListener negativeOnclickListener;

    public interface OnBaseDialogClickListener {
        void onClick(BaseDialog baseDialog);
    }

    public BaseDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    public BaseDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.base_dialog);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);

        //初始化界面控件
        initView();
        //初始化界面数据
        initData();
        //初始化界面控件的事件
        initEvent();
    }


    /**
     * 初始化界面控件
     */
    private void initView() {

        if (!TextUtils.isEmpty(title)) {
            TextView titleView = (TextView) findViewById(R.id.title_text);
            titleView.setText(title);
        }
        Button btn_cofirm = (Button) findViewById(R.id.cancel_button);
        if (positiviOnclickListener != null) {
            btn_cofirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    positiviOnclickListener.onClick(BaseDialog.this);
                }
            });
        }


        Button btn_cancle = (Button) findViewById(R.id.confirm_button);
        if (negativeOnclickListener != null) {
            btn_cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    negativeOnclickListener.onClick(BaseDialog.this);
                }
            });
        }

        if (myContentView != null) {//如果内容区域要显示其他的View的话
//            LinearLayout mContentLayout = (LinearLayout) findViewById(R.id.content);
//            mContentLayout.removeAllViews();
//            mContentLayout.addView(myContentView);

        }


    }

    private void initData() {
    }

    private void initEvent() {
    }

    public void setMyContentView(View myContentView) {
        this.myContentView = myContentView;
    }

    public void setPositiviOnclickListener(OnBaseDialogClickListener onBaseDialogClickListener) {
        positiviOnclickListener = onBaseDialogClickListener;
    }


    public void setNegativeOnclickListener(OnBaseDialogClickListener onBaseDialogClickListener) {
        negativeOnclickListener = onBaseDialogClickListener;
    }
}

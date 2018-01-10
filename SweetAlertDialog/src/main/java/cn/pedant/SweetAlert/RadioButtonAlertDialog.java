package cn.pedant.SweetAlert;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class RadioButtonAlertDialog extends Dialog implements View.OnClickListener {
    private View mDialogView;
    private AnimationSet mModalInAnim;
    private AnimationSet mModalOutAnim;
    private Animation mOverlayOutAnim;
    private TextView mTitleTextView;
    private TextView mContentTextView;
    private String mTitleText;
    private String mContentText;
    private boolean mShowCancel;
    private boolean mShowContent;
    private String mCancelText;
    private String mConfirmText;
//    private int mAlertType;

    private Button mConfirmButton;
    private Button mCancelButton;
    private OnSweetClickListener mCancelClickListener;
    private OnSweetClickListener mConfirmClickListener;
    private boolean mCloseFromCancel;

    String defaultValue;
    List<DictionariesEntity> list;

    RecyclerView recycleview;
    RadioAdapter adapter;
    Context mContext;

    public interface OnSweetClickListener {
        public void onClick(DictionariesEntity dictionariesEntity);
    }


    public RadioButtonAlertDialog(Context context) {
        super(context, cn.pedant.SweetAlert.R.style.alert_dialog);
        mContext = context;
        setCancelable(true);
        setCanceledOnTouchOutside(false);

        mModalInAnim = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), cn.pedant.SweetAlert.R.anim.modal_in);
        mModalOutAnim = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), cn.pedant.SweetAlert.R.anim.modal_out);
        mModalOutAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mDialogView.setVisibility(View.GONE);
                mDialogView.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mCloseFromCancel) {
                            RadioButtonAlertDialog.super.cancel();
                        } else {
                            RadioButtonAlertDialog.super.dismiss();
                        }
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        // dialog overlay fade out
        mOverlayOutAnim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                WindowManager.LayoutParams wlp = getWindow().getAttributes();
                wlp.alpha = 1 - interpolatedTime;
                getWindow().setAttributes(wlp);
            }
        };
        mOverlayOutAnim.setDuration(120);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.radio_dialog);

        mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);
        mTitleTextView = (TextView) findViewById(cn.pedant.SweetAlert.R.id.title_text);
        mContentTextView = (TextView) findViewById(cn.pedant.SweetAlert.R.id.content_text);

        mConfirmButton = (Button) findViewById(cn.pedant.SweetAlert.R.id.confirm_button);
        mCancelButton = (Button) findViewById(cn.pedant.SweetAlert.R.id.cancel_button);
        mConfirmButton.setOnClickListener(this);
        mCancelButton.setOnClickListener(this);

        setTitleText(mTitleText);
        setContentText(mContentText);
        setCancelText(mCancelText);
        setConfirmText(mConfirmText);

        recycleview = (RecyclerView) findViewById(R.id.recycleview);


        adapter = new RadioAdapter(mContext, defaultValue, list, new RadioAdapter.OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                if (mCancelClickListener != null) {
                    mCancelClickListener.onClick(list.get(position));
                    dismissWithAnimation();
                } else {
                    dismissWithAnimation();
                }
            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });
        recycleview.setAdapter(adapter);
     /*   recycleview.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        recycleview.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL));*/
     /*   LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                homeAdapter.getItemCount() * Utils.getHeightInPx(this)/12);*/
        recycleview.setLayoutManager(new GridLayoutManager(mContext, 1){

        });

    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public List<DictionariesEntity> getList() {
        return list;
    }

    public void setList(List<DictionariesEntity> list) {
        this.list = list;
    }


    private void restore() {
        mConfirmButton.setVisibility(View.VISIBLE);
        mConfirmButton.setBackgroundResource(cn.pedant.SweetAlert.R.drawable.blue_button_background);
    }


    public String getTitleText() {
        return mTitleText;
    }

    public RadioButtonAlertDialog setTitleText(String text) {
        mTitleText = text;
        if (mTitleTextView != null && mTitleText != null) {
            mTitleTextView.setText(mTitleText);
        }
        return this;
    }


    public String getContentText() {
        return mContentText;
    }

    public RadioButtonAlertDialog setContentText(String text) {
        mContentText = text;
        if (mContentTextView != null && mContentText != null) {
            showContentText(true);
            mContentTextView.setText(mContentText);
        }
        return this;
    }

    public boolean isShowCancelButton() {
        return mShowCancel;
    }

    public RadioButtonAlertDialog showCancelButton(boolean isShow) {
        mShowCancel = isShow;
        if (mCancelButton != null) {
            mCancelButton.setVisibility(mShowCancel ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    public boolean isShowContentText() {
        return mShowContent;
    }

    public RadioButtonAlertDialog showContentText(boolean isShow) {
        mShowContent = isShow;
        if (mContentTextView != null) {
            mContentTextView.setVisibility(mShowContent ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    public String getCancelText() {
        return mCancelText;
    }

    public RadioButtonAlertDialog setCancelText(String text) {
        mCancelText = text;
        if (mCancelButton != null && mCancelText != null) {
            showCancelButton(true);
            mCancelButton.setText(mCancelText);
        }
        return this;
    }

    public String getConfirmText() {
        return mConfirmText;
    }

    public RadioButtonAlertDialog setConfirmText(String text) {
        mConfirmText = text;
        if (mConfirmButton != null && mConfirmText != null) {
            mConfirmButton.setText(mConfirmText);
        }
        return this;
    }

    public RadioButtonAlertDialog setCancelClickListener(OnSweetClickListener listener) {
        mCancelClickListener = listener;
        return this;
    }

    public RadioButtonAlertDialog setConfirmClickListener(OnSweetClickListener listener) {
        mConfirmClickListener = listener;
        return this;
    }

    protected void onStart() {
        mDialogView.startAnimation(mModalInAnim);
    }

    /**
     * The real Dialog.cancel() will be invoked async-ly after the animation finishes.
     */
    @Override
    public void cancel() {
        dismissWithAnimation(true);
    }

    /**
     * The real Dialog.dismiss() will be invoked async-ly after the animation finishes.
     */
    public void dismissWithAnimation() {
        dismissWithAnimation(false);
    }

    private void dismissWithAnimation(boolean fromCancel) {
        mCloseFromCancel = fromCancel;
        mConfirmButton.startAnimation(mOverlayOutAnim);
        mDialogView.startAnimation(mModalOutAnim);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == cn.pedant.SweetAlert.R.id.cancel_button) {
            if (mCancelClickListener != null) {
                DictionariesEntity dictionariesEntity = new DictionariesEntity("1", "");
                mCancelClickListener.onClick(dictionariesEntity);

            } else {
                dismissWithAnimation();
            }
        } else if (v.getId() == cn.pedant.SweetAlert.R.id.confirm_button) {
            if (mConfirmClickListener != null) {
                mConfirmClickListener.onClick(new DictionariesEntity("1", ""));
            } else {
                dismissWithAnimation();
            }
        }
    }

}
package com.pvirtech.pzpolice.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.main.AppValue;
import com.pvirtech.pzpolice.utils.ListUtils;
import com.pvirtech.pzpolice.utils.LoadingViewProgress;
import com.pvirtech.pzpolice.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by youpengda on 2017/2/8.
 */

public class BaseFragment extends Fragment implements BaseView {
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    public Context mContext;
    public String TAG;
    LoadingViewProgress loadingViewProgress = new LoadingViewProgress();
    View view;
    LinearLayout llHttpError;
    LinearLayout llNoResult;
    LinearLayout llUnnormal;
    List<Disposable> disposableList = new ArrayList<>();
    ReadWriteLock rwl = new ReentrantReadWriteLock();

    public void initView(View view) {
        ButterKnife.bind(this, view);//绑定framgent
        this.view = view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    public void initInfoTitleView(View view) {
        ButterKnife.bind(this, view);//绑定framgent
        TextView tv_info_name = (TextView) view.findViewById(R.id.tv_info_name);
        tv_info_name.setText(AppValue.getInstance().getmUserInfo().getNAME());
        TextView tv_info_department = (TextView) view.findViewById(R.id.tv_info_department);
        
        tv_info_department.setText(AppValue.getInstance().getmUserInfo().getUNIT_CODE());
        TextView tv_info_date = (TextView) view.findViewById(R.id.tv_info_date);
        tv_info_date.setText(TimeUtil.getYMD());
        this.view = view;
    }

    public void initTitleView(View view, String title) {
        ButterKnife.bind(this, view);//绑定framgent
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle(title);//设置主标题
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        this.view = view;
    }


    public void initInfoTitleView(View view, String title) {
        ButterKnife.bind(this, view);//绑定framgent
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle(title);//设置主标题
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        TextView tv_info_name = (TextView) view.findViewById(R.id.tv_info_name);
        tv_info_name.setText(AppValue.getInstance().getmUserInfo().getNAME());
        TextView tv_info_department = (TextView) view.findViewById(R.id.tv_info_department);
        tv_info_department.setText(AppValue.getInstance().getmUserInfo().getUNIT_CODE());
        TextView tv_info_date = (TextView) view.findViewById(R.id.tv_info_date);
        tv_info_date.setText(TimeUtil.getYMD());
        this.view = view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
        loadingViewProgress.hideDialogForLoading();//防止内存泄漏
    }

    public void clearHttp() {
        if (compositeDisposable != null) {
            rwl.readLock().lock();
            try {
                if (!ListUtils.isEmpty(disposableList)) {
                    for (Disposable disposable : disposableList) {
                        compositeDisposable.remove(disposable);
                    }
                    disposableList.clear();
                }
            } finally {
                rwl.readLock().unlock();
            }

        }
    }

    @Override
    public void showLoading(String msg) {
        if (TextUtils.isEmpty(msg)) {
            loadingViewProgress.showDialogForLoading(mContext, "正在加载。。。", false);
        } else {
            loadingViewProgress.showDialogForLoading(mContext, msg, false);
        }
    }

    @Override
    public void hideLoading() {
        loadingViewProgress.hideDialogForLoading();
    }

    @Override
    public void showError(String msg) {
        loadingViewProgress.hideDialogForLoading();
        if (!TextUtils.isEmpty(msg)) {
            Toasty.error(mContext, msg).show();
        } else {
            Toasty.error(mContext, getString(R.string.submint_failed)).show();
        }
    }

    @Override
    public void showException(String msg) {

    }

    @Override
    public void showNetError() {

    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public void setCompositeDisposable(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    public void addDisposable(Disposable disposable) {
        rwl.readLock().lock();
        try {
            getCompositeDisposable().add(disposable);
            disposableList.add(disposable);
        } finally {
            rwl.readLock().unlock();
        }
    }

    private void findErrorViewID() {
        if (null == llUnnormal) {
            llUnnormal = (LinearLayout) view.findViewById(R.id.ll_unnormal);
        }
        if (null == llHttpError) {
            llHttpError = (LinearLayout) view.findViewById(R.id.ll_http_error);
        }
        if (null == llNoResult) {
            llNoResult = (LinearLayout) view.findViewById(R.id.ll_no_result);
        }
    }

    /**
     * 显示网路错误
     */
    public void showHttpError() {
        findErrorViewID();
        llUnnormal.setVisibility(View.VISIBLE);
        llNoResult.setVisibility(View.GONE);
        llHttpError.setVisibility(View.VISIBLE);
    }

    /**
     * 显示没有数据
     */
    public void showHttpNoResult() {
        findErrorViewID();
        llUnnormal.setVisibility(View.VISIBLE);
        llHttpError.setVisibility(View.GONE);
        llNoResult.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏异常
     */
    public void hideHttpView() {
        findErrorViewID();
        llUnnormal.setVisibility(View.GONE);
    }

}

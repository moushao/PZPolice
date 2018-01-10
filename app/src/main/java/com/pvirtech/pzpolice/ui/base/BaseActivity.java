package com.pvirtech.pzpolice.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.main.AppLoginActivity;
import com.pvirtech.pzpolice.main.AppValue;
import com.pvirtech.pzpolice.utils.AppManager;
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
 * Created by pd on 2016/9/19.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    public Context mContext;
    public String TAG;
    LoadingViewProgress loadingViewProgress = new LoadingViewProgress();
    LinearLayout llHttpError;
    LinearLayout llNoResult;
    LinearLayout llUnnormal;
    List<Disposable> disposableList = new ArrayList<>();
    ReadWriteLock rwl = new ReentrantReadWriteLock();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (TextUtils.isEmpty(AppValue.getInstance().getmUserInfo().getUSER_ID()) || TextUtils.isEmpty(AppValue.getInstance().getmUserInfo()
                .getROLE_ID())) {
            Toasty.error(this, "用户信息丢失请重新登录").show();
            Intent intent = new Intent(this, AppLoginActivity.class);
            this.startActivity(intent);
        }
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        mContext = this;
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
        loadingViewProgress.hideDialogForLoading();//防止内存泄漏
        AppManager.getAppManager().finishActivity(this);
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

    public void initTitleView(String title) {
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);//设置主标题
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // 4.0以上Navigation默认false
            actionBar.setDisplayHomeAsUpEnabled(true);
            // Title默认true
            actionBar.setDisplayShowTitleEnabled(true);
            // Logo默认true
            actionBar.setDisplayUseLogoEnabled(true);
        }


    }

    public void initInfoTitleView(String title) {
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);//设置主标题
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // 4.0以上Navigation默认false
            actionBar.setDisplayHomeAsUpEnabled(true);
            // Title默认true
            actionBar.setDisplayShowTitleEnabled(true);
            // Logo默认true
            actionBar.setDisplayUseLogoEnabled(true);
        }
        TextView tv_info_name = (TextView) findViewById(R.id.tv_info_name);
        tv_info_name.setText(AppValue.getInstance().getmUserInfo().getNAME());
        TextView tv_info_department = (TextView) findViewById(R.id.tv_info_department);
        tv_info_department.setText(AppValue.getInstance().getmUserInfo().getUNIT_CODE());
        TextView tv_info_date = (TextView) findViewById(R.id.tv_info_date);
        tv_info_date.setText(TimeUtil.getYMD());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showLoading(String msg) {
        if (TextUtils.isEmpty(msg)) {
            loadingViewProgress.showDialogForLoading(this, "正在加载。。。", false);
        } else {
            loadingViewProgress.showDialogForLoading(this, msg, false);
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

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        System.out.println("");
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (loadingViewProgress.getIsShowing()) {
                loadingViewProgress.hideDialogForLoading();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
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

    public void setCompositeDisposable(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }


    private void findErrorViewID() {
        if (null == llUnnormal) {
            llUnnormal = (LinearLayout) findViewById(R.id.ll_unnormal);
        }
        if (null == llHttpError) {
            llHttpError = (LinearLayout) findViewById(R.id.ll_http_error);
        }
        if (null == llNoResult) {
            llNoResult = (LinearLayout) findViewById(R.id.ll_no_result);
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

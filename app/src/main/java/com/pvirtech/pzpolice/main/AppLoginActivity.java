package com.pvirtech.pzpolice.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.UserInfo;
import com.pvirtech.pzpolice.http.HttpResult;
import com.pvirtech.pzpolice.http.HttpSubmit;
import com.pvirtech.pzpolice.http.HttpSubmitUtil;
import com.pvirtech.pzpolice.http.ResultCode;
import com.pvirtech.pzpolice.http.RetrofitHttp;
import com.pvirtech.pzpolice.ui.activity.bottomnavigationbar.HomeMainActivity;
import com.pvirtech.pzpolice.utils.AppManager;
import com.pvirtech.pzpolice.utils.KeyBoardUtils;
import com.pvirtech.pzpolice.utils.L;
import com.pvirtech.pzpolice.utils.PreferenceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class AppLoginActivity extends Activity {
    String tag = "AppLoginActivity";
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_app_login);
        ButterKnife.bind(this);
        mContext = this;
        initView();
        AppManager.getAppManager().finishAllActivity();
        PreferenceUtils.setPrefString(mContext, "userInfo", "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @BindView(R.id.input_layout)
    LinearLayout input_layout;

    @BindView(R.id.layout_progress)
    LinearLayout layout_progress;

    @BindView(R.id.main_btn_login)
    Button main_btn_login;
    @BindView(R.id.set_ip)
    Button mIp;

    @BindView(R.id.edt_username)
    EditText edt_username;

    @BindView(R.id.input_element)
    LinearLayout input_element;

    @BindView(R.id.linear_username)
    LinearLayout linear_username;

    @BindView(R.id.linear_password)
    LinearLayout linear_password;

    @BindView(R.id.edt_password)
    EditText edt_password;

    private void initView() {
        edt_username.setText(PreferenceUtils.getPrefString(mContext, "userName", ""));
        edt_password.setText(PreferenceUtils.getPrefString(mContext, "passWord", ""));
    }

    @OnClick(R.id.main_btn_login)
    void Login(View view) {

        String userName = edt_username.getText().toString();
        String passWord = edt_password.getText().toString();
        boolean cancel = false;
        View focusView = null;
        if (TextUtils.isEmpty(userName)) {
            edt_username.setError(getString(R.string.error_field_required));
            focusView = edt_username;
            cancel = true;
        }

        if (TextUtils.isEmpty(passWord)) {
            edt_password.setError(getString(R.string.error_field_required));
            focusView = edt_password;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            Login(userName, passWord);
            KeyBoardUtils.closeKeybord(edt_password, mContext);
            KeyBoardUtils.closeKeybord(edt_username, mContext);
        }
    }

    @OnClick(R.id.set_ip)
    void gotoSetIP(){
        startActivity(new Intent(mContext,IPActivity.class));
    }
    private void Login(String userName, String passWord) {
        /**
         * 保存用户名密码到本地
         */
        PreferenceUtils.setPrefString(mContext, "userName", userName);
        PreferenceUtils.setPrefString(mContext, "passWord", passWord);
        /**
         * 处理提交数据
         */
        JsonObject data = new JsonObject();
        data.addProperty("userName", userName);
        data.addProperty("pwd", passWord);
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);
        layout_progress.setVisibility(View.VISIBLE);
        main_btn_login.setVisibility(View.GONE);
        /**
         * 网路访问登录
         */

        RetrofitHttp.provideClientApi().login(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers
                        .mainThread()).subscribe(new Consumer<HttpResult<UserInfo>>() {
            @Override
            public void accept(@NonNull HttpResult<UserInfo> response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    PreferenceUtils.setPrefBoolean(mContext, "IsAutoLogin", true);
                    PreferenceUtils.setPrefString(mContext, "IsAutoLoginTime", String.valueOf(System
                            .currentTimeMillis()));
                    UserInfo userInfo = (UserInfo) response.getData();
                    AppValue.getInstance().setmUserInfo(userInfo);
                    Gson gson = new Gson();
                    PreferenceUtils.setPrefString(mContext, "userInfo", gson.toJson(userInfo));
                    Intent intent = new Intent(mContext, HomeMainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    if (response.getResultMessage().equals("pwd")) {
                        edt_password.setError("用户名或密码错误");
                    }
                    layout_progress.setVisibility(View.INVISIBLE);
                    main_btn_login.setVisibility(View.VISIBLE);
                    L.d("失败");
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                layout_progress.setVisibility(View.INVISIBLE);
                main_btn_login.setVisibility(View.VISIBLE);
                L.d("失败");
                edt_password.setError(mContext.getResources().getString(R.string.network_err));
            }
        });

    }

}

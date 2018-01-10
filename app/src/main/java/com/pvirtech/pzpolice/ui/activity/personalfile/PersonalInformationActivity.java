package com.pvirtech.pzpolice.ui.activity.personalfile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.UserInfo;
import com.pvirtech.pzpolice.enumeration.RoleEnum;
import com.pvirtech.pzpolice.http.HttpResult;
import com.pvirtech.pzpolice.http.HttpSubmit;
import com.pvirtech.pzpolice.http.ResultCode;
import com.pvirtech.pzpolice.http.RetrofitHttp;
import com.pvirtech.pzpolice.main.Constant;
import com.pvirtech.pzpolice.ui.base.BaseActivity;
import com.pvirtech.pzpolice.utils.L;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 查看用户的个人信息
 */
public class PersonalInformationActivity extends BaseActivity {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_position)
    TextView tvPosition;
    @BindView(R.id.tv_phone_num)
    TextView tvPhoneNum;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_phone)
    TextView tvPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);
        ButterKnife.bind(this);
        initTitleView("个人信息");
        TAG = "PersonalInformationActivity";
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        String USER_ID = intent.getStringExtra(Constant.INTENT_BUNDLE);
        if (!TextUtils.isEmpty(USER_ID)) {
            getData(USER_ID);
        }
    }

    private void getData(String USER_ID) {
        /**
         * 处理提交数据
         */
        JsonObject data = new JsonObject();
        data.addProperty("USER_ID", USER_ID);
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);

        getCompositeDisposable().add(RetrofitHttp.provideClientApi().apppoliceUserinfo(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers
                        .mainThread()).subscribe(new Consumer<HttpResult<UserInfo>>() {
            @Override
            public void accept(@NonNull HttpResult<UserInfo> response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    hideLoading();
                    UserInfo userInfo = response.getData();
                    if (null != userInfo) {
                        initInfoData(userInfo);
                    }
                } else {
                    showError(response.getResultMessage());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                L.d("Error");
                showError("");
            }
        }));

    }

    private void initInfoData(UserInfo userInfo) {
        tvName.setText(userInfo.getNAME());

        //职位
        for (RoleEnum myEnum : RoleEnum.values()) {
            if (myEnum.getValue().getRoleId().equals(userInfo.getROLE_ID())) {
                tvPosition.setText(myEnum.getValue().getRoleName());
                break;
            }
        }
        tvEmail.setText(userInfo.getEMAIL());
        tvPhone.setText(userInfo.getPHONE());

    }


    @OnClick({R.id.ll_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_phone:
                if (!TextUtils.isEmpty(tvPhone.getText().toString())) {
                    call(tvPhone.getText().toString());
                }
                break;
        }
    }

    private void call(String phone) {
//        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
//        startActivity(intent);
        Intent intentPhone = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        try {
            startActivity(intentPhone);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);*/

    }
}

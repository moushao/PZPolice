package com.pvirtech.pzpolice.ui.activity.personalfile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.PersonalFileBasicFragmentEntity;
import com.pvirtech.pzpolice.http.HttpResult;
import com.pvirtech.pzpolice.http.HttpSubmit;
import com.pvirtech.pzpolice.http.HttpSubmitUtil;
import com.pvirtech.pzpolice.http.ResultCode;
import com.pvirtech.pzpolice.http.RetrofitHttp;
import com.pvirtech.pzpolice.ui.base.BaseFragment;
import com.pvirtech.pzpolice.utils.L;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/12.
 * 人员基本信息主界面
 */

public class PersonalFileBasicFragment extends BaseFragment {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.tv_original)
    TextView tvOriginal;
    @BindView(R.id.tv_identify)
    TextView tvIdentify;
    @BindView(R.id.tv_phone_num)
    TextView tvPhoneNum;
    @BindView(R.id.tv_political_status)
    TextView tvPoliticalStatus;
    @BindView(R.id.tv_time_in)
    TextView tvTimeIn;
    @BindView(R.id.tv_reward)
    TextView tvReward;
    @BindView(R.id.tv_special)
    TextView tvSpecial;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_living)
    TextView tvLiving;
    @BindView(R.id.tv_family_living)
    TextView tvFamilyLiving;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_personal_file_basic, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        /**
         * 处理提交数据
         */
        JsonObject data = new JsonObject();
        data.addProperty("infoType", 1);
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);
        showLoading("");
        getCompositeDisposable().add(RetrofitHttp.provideClientApi().apppersonalBasicFragment(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers
                        .mainThread()).subscribe(new Consumer<HttpResult<PersonalFileBasicFragmentEntity>>() {
            @Override
            public void accept(@NonNull HttpResult<PersonalFileBasicFragmentEntity> response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    PersonalFileBasicFragmentEntity personalFileBasicFragmentEntity = response.getData();
                    tvName.setText(personalFileBasicFragmentEntity.getNAME());
                    tvSex.setText(personalFileBasicFragmentEntity.getSEX() + "");
                    tvAge.setText(personalFileBasicFragmentEntity.getAGE() + "");
                    tvBirthday.setText(personalFileBasicFragmentEntity.getBIRTHDAY());
                    tvOriginal.setText(personalFileBasicFragmentEntity.getORIGIN());
                    tvIdentify.setText(personalFileBasicFragmentEntity.getUSER_ID_CARD());
                    tvPhoneNum.setText(personalFileBasicFragmentEntity.getPHONE());
                    tvPoliticalStatus.setText(personalFileBasicFragmentEntity.getPOLITICAL());
                    tvTimeIn.setText(personalFileBasicFragmentEntity.getPARTY_TIME());
                    tvReward.setText(personalFileBasicFragmentEntity.getWins());
                    tvSpecial.setText(personalFileBasicFragmentEntity.getSPECIAL_SKILL());
                    tvLocation.setText(personalFileBasicFragmentEntity.getPERMANENT_ADDRESS());
                    tvLiving.setText(personalFileBasicFragmentEntity.getPRESENT_ADDRESS());
                    tvFamilyLiving.setText(personalFileBasicFragmentEntity.getPARENT_ADDRESS());
                    hideLoading();

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

package com.pvirtech.pzpolice.ui.activity.personalfile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.PersonalFileEducationFragmentEntity;
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
 * 人员教育 信息主界面
 * 教育
 */

public class PersonalFileEducationFragment extends BaseFragment {

    @BindView(R.id.tv_car_license_type)
    TextView tvCarLicenseType;
    @BindView(R.id.tv_car_license_date)
    TextView tvCarLicenseDate;
    @BindView(R.id.tv_highest_education)
    TextView tvHighestEducation;
    @BindView(R.id.tv_graduation_time1)
    TextView tvGraduationTime1;
    @BindView(R.id.tv_graduation_school1)
    TextView tvGraduationSchool1;
    @BindView(R.id.tv_academic_qualifications)
    TextView tvAcademicQualifications;
    @BindView(R.id.tv_graduation_time2)
    TextView tvGraduationTime2;
    @BindView(R.id.tv_graduation_school2)
    TextView tvGraduationSchool2;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_personal_file_education, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        /**
         * 处理提交数据
         */
        JsonObject data = new JsonObject();
        data.addProperty("infoType", 3);
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);
        showLoading("");

        getCompositeDisposable().add(RetrofitHttp.provideClientApi().apppersonalFileEducationFragment(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers
                        .mainThread()).subscribe(new Consumer<HttpResult<PersonalFileEducationFragmentEntity>>() {
            @Override
            public void accept(@NonNull HttpResult<PersonalFileEducationFragmentEntity> response) throws Exception {

                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    PersonalFileEducationFragmentEntity personalFileEducationFragmentEntity = response.getData();
                    tvCarLicenseType.setText(personalFileEducationFragmentEntity.getDRIVERS_TYPE()+"");
                    tvCarLicenseDate.setText(personalFileEducationFragmentEntity.getGET_DRIVERS_TIME());
                    tvHighestEducation.setText(personalFileEducationFragmentEntity.getMAX_EDUCATION()+"");
                    tvGraduationTime1.setText(personalFileEducationFragmentEntity.getEDUCATE_TIME());
                    tvGraduationSchool1.setText(personalFileEducationFragmentEntity.getEDUCATE_SCHOOL());
                    tvAcademicQualifications.setText(personalFileEducationFragmentEntity.getNAME());
                    tvGraduationTime2.setText(personalFileEducationFragmentEntity.getEDUCATE_TIME());
                    tvGraduationSchool2.setText(personalFileEducationFragmentEntity.getEDUCATE_SCHOOL());
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

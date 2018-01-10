package com.pvirtech.pzpolice.ui.activity.personalfile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.PersonalFileWorkFragmentEntity;
import com.pvirtech.pzpolice.http.HttpResult;
import com.pvirtech.pzpolice.http.HttpSubmit;
import com.pvirtech.pzpolice.http.HttpSubmitUtil;
import com.pvirtech.pzpolice.http.ResultCode;
import com.pvirtech.pzpolice.http.RetrofitHttp;
import com.pvirtech.pzpolice.ui.adapter.PersonalFileWorkResumeAdapter;
import com.pvirtech.pzpolice.ui.base.BaseFragment;
import com.pvirtech.pzpolice.utils.L;
import com.pvirtech.pzpolice.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/12.
 * 人员工作信息主界面
 */

public class PersonalFileWorkFragment extends BaseFragment {

    PersonalFileWorkResumeAdapter resumeAdapter;
    @BindView(R.id.tv_file_num)
    TextView tvFileNum;
    @BindView(R.id.tv_work_place)
    TextView tvWorkPlace;
    @BindView(R.id.tv_post)
    TextView tvPost;
    @BindView(R.id.tv_post_time)
    TextView tvPostTime;
    @BindView(R.id.tv_post_rank)
    TextView tvPostRank;
    @BindView(R.id.tv_post_rank_time)
    TextView tvPostRankTime;
    @BindView(R.id.tv_time_to_GuangMing)
    TextView tvTimeToGuangMing;
    @BindView(R.id.tv_time_to_police)
    TextView tvTimeToPolice;
    @BindView(R.id.tv_time_to_job)
    TextView tvTimeToJob;
    @BindView(R.id.tv_cv_recycleview)
    RecyclerView tvCvRecycleview;
    private List<PersonalFileWorkFragmentEntity.ResumesBean> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_personal_file_work, container, false);
        initView(view);
//        initsPersonalFileWorkResume();

        resumeAdapter = new PersonalFileWorkResumeAdapter(mContext, list, new PersonalFileWorkResumeAdapter.OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });
        tvCvRecycleview.setAdapter(resumeAdapter);
        tvCvRecycleview.setLayoutManager(new GridLayoutManager(mContext, 1));
        initData();
        return view;
    }

    private void initData() {
        /**
         * 处理提交数据
         */
        JsonObject data = new JsonObject();
        data.addProperty("infoType", 2);
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);
        showLoading("");

        getCompositeDisposable().add(RetrofitHttp.provideClientApi().apppersonalFileWorkFragment(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers
                        .mainThread()).subscribe(new Consumer<HttpResult<PersonalFileWorkFragmentEntity>>() {
            @Override
            public void accept(@NonNull HttpResult<PersonalFileWorkFragmentEntity> response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    PersonalFileWorkFragmentEntity personalFileWorkFragmentEntity = response.getData();
                    list = personalFileWorkFragmentEntity.getResumes();
                    if (!ListUtils.isEmpty(list))
                        resumeAdapter.setList(list);
                    tvFileNum.setText(personalFileWorkFragmentEntity.getRECORD_ID());
                    tvWorkPlace.setText(personalFileWorkFragmentEntity.getMANAGE_WORK());
                    tvPost.setText(personalFileWorkFragmentEntity.getNOW_DUTY());
                    tvPostTime.setText(personalFileWorkFragmentEntity.getNOW_TAKE_OFFICE_TIME());
                    tvPostRank.setText(personalFileWorkFragmentEntity.getNOW_LEVEL());
                    tvPostRankTime.setText(personalFileWorkFragmentEntity.getNOW_LEVEL_TIME());
                    tvTimeToGuangMing.setText(personalFileWorkFragmentEntity.getPART_GM_POLICE_TIME());
                    tvTimeToPolice.setText(personalFileWorkFragmentEntity.getPART_POLICE_TIME());
                    tvTimeToJob.setText(personalFileWorkFragmentEntity.getPART_WORK_TIME());

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

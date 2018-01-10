package com.pvirtech.pzpolice.ui.activity.personalfile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonObject;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.PersonalFileFamilyFragmentEntity;
import com.pvirtech.pzpolice.http.HttpResult;
import com.pvirtech.pzpolice.http.HttpSubmit;
import com.pvirtech.pzpolice.http.HttpSubmitUtil;
import com.pvirtech.pzpolice.http.ResultCode;
import com.pvirtech.pzpolice.http.RetrofitHttp;
import com.pvirtech.pzpolice.ui.adapter.PersonalFileFamilyMemberAdapter;
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
 * 人员家庭信息主界面
 * 家庭
 */

public class PersonalFileFamilyFragment extends BaseFragment {

    PersonalFileFamilyMemberAdapter memberAdapter;

    @BindView(R.id.family_member_recyclerview)
    RecyclerView familyMemberRecyclerview;
    private List<PersonalFileFamilyFragmentEntity> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_personal_file_family, container, false);
        initView(view);
        memberAdapter = new PersonalFileFamilyMemberAdapter(mContext, list, new PersonalFileFamilyMemberAdapter.OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });
        familyMemberRecyclerview.setAdapter(memberAdapter);
        familyMemberRecyclerview.setLayoutManager(new GridLayoutManager(mContext, 1));
        initData();
        return view;
    }

    private void initData() {
        /**
         * 处理提交数据
         */
        JsonObject data = new JsonObject();
        data.addProperty("infoType", 4);
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);
        showLoading("");
        getCompositeDisposable().add(RetrofitHttp.provideClientApi().apppersonalFileFamilyFragment(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers
                        .mainThread()).subscribe(new Consumer<HttpResult<List<PersonalFileFamilyFragmentEntity>>>() {
            @Override
            public void accept(@NonNull HttpResult<List<PersonalFileFamilyFragmentEntity>> response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    List<PersonalFileFamilyFragmentEntity> list = response.getData();
                    if (!ListUtils.isEmpty(list))
                        memberAdapter.setmData(list);
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

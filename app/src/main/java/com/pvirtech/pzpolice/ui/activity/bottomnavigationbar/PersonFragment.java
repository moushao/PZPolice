package com.pvirtech.pzpolice.ui.activity.bottomnavigationbar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonObject;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.http.HttpResult;
import com.pvirtech.pzpolice.http.HttpSubmit;
import com.pvirtech.pzpolice.http.HttpSubmitUtil;
import com.pvirtech.pzpolice.http.ResultCode;
import com.pvirtech.pzpolice.http.RetrofitHttp;
import com.pvirtech.pzpolice.main.Constant;
import com.pvirtech.pzpolice.ui.activity.personalfile.PersonalInformationActivity;
import com.pvirtech.pzpolice.ui.base.BaseFragment;
import com.pvirtech.pzpolice.utils.L;
import com.pvirtech.pzpolice.utils.ListUtils;
import com.tonypy.tonypy.addressbooks.adpter.PersonDetailsAdapter;
import com.tonypy.tonypy.addressbooks.adpter.SectionedSpanSizeLookup;
import com.tonypy.tonypy.addressbooks.entity.Group;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * 人员薄主页
 */
public class PersonFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private PersonDetailsAdapter mAdapter;
    List<Group> gruopLists = new ArrayList<>();

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        initView(view);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.selct_recyclerView);
        mAdapter = new PersonDetailsAdapter(mContext, new PersonDetailsAdapter.OnRecyclerViewListener() {
            @Override
            public void onItemClick(Group.GroupUSERBean groupUSERBean) {
                Intent intent = new Intent(mContext, PersonalInformationActivity.class);
                intent.putExtra(Constant.INTENT_BUNDLE, groupUSERBean.getUser_ID());
                mContext.startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(Group.GroupUSERBean groupUSERBean) {
                return false;
            }
        });
        GridLayoutManager manager = new GridLayoutManager(mContext, 4);
        //设置header
        manager.setSpanSizeLookup(new SectionedSpanSizeLookup(mAdapter, manager));
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        getPersons();
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void getPersons() {
        /**
         * 处理提交数据
         */
        JsonObject data = new JsonObject();
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);

        getCompositeDisposable().add(RetrofitHttp.provideClientApi().appgroupInfo(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<List<Group>>>() {
            @Override
            public void accept(@NonNull HttpResult<List<Group>> response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    if (!ListUtils.isEmpty(response.getData())) {
                        gruopLists = response.getData();
                        mAdapter.setData(gruopLists);
                        hideLoading();
                        hideHttpView();
                    } else {
                        showHttpNoResult();
                    }
                } else {
                    showHttpError();
                    showError(response.getResultMessage());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                L.d("Error");
                showHttpError();
                showError("");
            }
        }));
    }


    private void search(List<Group> gruopLists, String key) {
        List<Group> sraech = new ArrayList<>();
        for (Group group : gruopLists) {
            Group groupTemp = new Group();
            groupTemp.setGroup_ID(group.getGroup_ID());
            groupTemp.setGroup_NAME(group.getGroup_NAME());
//            groupTemp.setGroup_PARENT_ID(group.getGroup_PARENT_ID());
            groupTemp.setGroup_RESPOSE_NO(group.getGroup_RESPOSE_NO());
            groupTemp.setFlag(group.getFlag());
            List<Group.GroupUSERBean> groupUSERBeanTemp = new ArrayList<>();
            for (Group.GroupUSERBean groupUSERBean : group.getGroup_USER()) {
                if (groupUSERBean.getName().contains(key)) {
                    groupUSERBeanTemp.add(groupUSERBean);
                }
            }
            groupTemp.setGroup_USER(groupUSERBeanTemp);
            sraech.add(groupTemp);
        }
        mAdapter.setData(sraech);
    }

    @OnClick({R.id.ll_unnormal})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_unnormal:
                showLoading("");
                getPersons();
                break;
        }
    }
}

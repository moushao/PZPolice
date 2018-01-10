package com.pvirtech.pzpolice.ui.activity.work;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.enumeration.RoleEnum;
import com.pvirtech.pzpolice.http.HttpResult;
import com.pvirtech.pzpolice.http.HttpSubmit;
import com.pvirtech.pzpolice.http.HttpSubmitUtil;
import com.pvirtech.pzpolice.http.ResultCode;
import com.pvirtech.pzpolice.http.RetrofitHttp;
import com.pvirtech.pzpolice.main.Constant;
import com.pvirtech.pzpolice.ui.base.BaseActivity;
import com.pvirtech.pzpolice.utils.L;
import com.pvirtech.pzpolice.utils.ListUtils;
import com.tonypy.tonypy.addressbooks.adpter.SectionedSpanSizeLookup;
import com.tonypy.tonypy.addressbooks.adpter.SelectPersonAdapter;
import com.tonypy.tonypy.addressbooks.entity.Group;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class SelectPersonActivity extends BaseActivity {

    @BindView(R.id.view_one)
    View viewOne;
    @BindView(R.id.view_two)
    View viewTwo;
    @BindView(R.id.view_all)
    View viewAll;
    private RecyclerView mRecyclerView;
    private SelectPersonAdapter mAdapter;
    List<Group> gruopLists = new ArrayList<>();
    ArrayList<Group.GroupUSERBean> listSelected = new ArrayList<>();
    TextView all, confirm;
    int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_persons);
        ButterKnife.bind(this);
        all = (TextView) findViewById(R.id.all);
        confirm = (TextView) findViewById(R.id.confirm);
        initTitleView("请选择人员");
        initView();
        getPersons();
    }


    private void initView() {
        try {
            Intent intent = getIntent();
            type = intent.getIntExtra("type", Constant.RESULT_SELECT_PERSON);
        } catch (Exception e) {
            e.printStackTrace();
        }
        viewAll.setVisibility(View.VISIBLE);
        viewOne.setVisibility(View.INVISIBLE);
        viewTwo.setVisibility(View.INVISIBLE);

        mRecyclerView = (RecyclerView) findViewById(R.id.selct_recyclerView);
        mAdapter = new SelectPersonAdapter(this);
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        //设置header
        manager.setSpanSizeLookup(new SectionedSpanSizeLookup(mAdapter, manager));
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);


        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (all.getText().equals("全选")) {
                    for (Group group : mAdapter.getAllTagList()) {
                        for (Group.GroupUSERBean groupUSERBean : group.getGroup_USER()) {
                            groupUSERBean.setFlag(1);
                        }
                    }
                    all.setText("全不选");
                    mAdapter.setTitleFlag(true);
                } else {
                    for (Group group : mAdapter.getAllTagList()) {
                        for (Group.GroupUSERBean groupUSERBean : group.getGroup_USER()) {
                            groupUSERBean.setFlag(0);
                        }
                    }
                    all.setText("全选");
                    mAdapter.setTitleFlag(false);
                }
                mAdapter.refresh();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Group group : mAdapter.getAllTagList()) {
                    for (Group.GroupUSERBean groupUSERBean : group.getGroup_USER()) {
                        if (groupUSERBean.getFlag() == 1) {
                            listSelected.add(groupUSERBean);
                        }
                    }
                }
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(Constant.PERSON_SELECTED, listSelected);
                intent.putExtras(bundle);
                setResult(type, intent);
                finish();
            }
        });
    }

    private void getPersons() {
        showLoading("");
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
            public void accept(HttpResult<List<Group>> response) {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    if (!ListUtils.isEmpty(response.getData())) {
                        gruopLists = response.getData();
                        mAdapter.setData(gruopLists);
                        mRecyclerView.setAdapter(mAdapter);
                        hideHttpView();
                        hideLoading();
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
            public void accept(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
                showHttpError();
                L.d("Error");
                showError("");
            }
        }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_selectperson_toolbar, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.ab_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println("");
                if (TextUtils.isEmpty(query)) {
                    mAdapter.setData(gruopLists);
                } else {
                    search(gruopLists, query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println("");
                if (TextUtils.isEmpty(newText)) {
                    mAdapter.setData(gruopLists);
                } else {
                    search(gruopLists, newText);
                }
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mAdapter.setData(gruopLists);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ab_search:
                break;
        }
        return super.onOptionsItemSelected(item);
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

    @OnClick({R.id.tv_police, R.id.tv_auxiliary, R.id.tv_all, R.id.ll_unnormal})
    public void onViewClicked(View view) {
        List<String> type = new ArrayList<>();
        switch (view.getId()) {
            case R.id.tv_all:
                viewAll.setVisibility(View.VISIBLE);
                viewOne.setVisibility(View.INVISIBLE);
                viewTwo.setVisibility(View.INVISIBLE);
                mAdapter.setData(gruopLists);
                break;
            case R.id.tv_police:
                viewAll.setVisibility(View.INVISIBLE);
                viewOne.setVisibility(View.VISIBLE);
                viewTwo.setVisibility(View.INVISIBLE);
                type.add(RoleEnum.SHERIFF.getValue().getRoleId());
                type.add(RoleEnum.POLICE.getValue().getRoleId());
                filterPerson(gruopLists, type);
                break;
            case R.id.tv_auxiliary:
                viewAll.setVisibility(View.INVISIBLE);
                viewOne.setVisibility(View.INVISIBLE);
                viewTwo.setVisibility(View.VISIBLE);
                type.add(RoleEnum.AUXILIARY.getValue().getRoleId());
                filterPerson(gruopLists, type);
                break;
            case R.id.ll_unnormal:
                getPersons();
                break;
        }
    }

    private void filterPerson(List<Group> gruopLists, List<String> type) {
        List<Group> filter = new ArrayList<>();
        for (Group group : gruopLists) {
            Group groupTemp = new Group();
            groupTemp.setGroup_ID(group.getGroup_ID());
            groupTemp.setGroup_NAME(group.getGroup_NAME());
//            groupTemp.setGroup_PARENT_ID(group.getGroup_PARENT_ID());
            groupTemp.setGroup_RESPOSE_NO(group.getGroup_RESPOSE_NO());
            groupTemp.setFlag(group.getFlag());
            List<Group.GroupUSERBean> groupUSERBeanTemp = new ArrayList<>();
            for (Group.GroupUSERBean groupUSERBean : group.getGroup_USER()) {
                for (String strType : type) {
                    if (groupUSERBean.getRole_ID().equals(strType)) {
                        groupUSERBeanTemp.add(groupUSERBean);
                    }
                }
            }
            groupTemp.setGroup_USER(groupUSERBeanTemp);
            filter.add(groupTemp);
        }
        mAdapter.setData(filter);
    }


}

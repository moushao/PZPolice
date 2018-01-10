package com.pvirtech.pzpolice.ui.activity.work;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.MyAssessmentEntity;
import com.pvirtech.pzpolice.entity.MyAssessmentListEntity;
import com.pvirtech.pzpolice.entity.MyTasksEntity;
import com.pvirtech.pzpolice.entity.ScoreEntity;
import com.pvirtech.pzpolice.enumeration.MyAssessmentEnum;
import com.pvirtech.pzpolice.http.HttpResult;
import com.pvirtech.pzpolice.http.HttpSubmit;
import com.pvirtech.pzpolice.http.HttpSubmitUtil;
import com.pvirtech.pzpolice.http.ResultCode;
import com.pvirtech.pzpolice.http.RetrofitHttp;
import com.pvirtech.pzpolice.main.AppValue;
import com.pvirtech.pzpolice.main.Constant;
import com.pvirtech.pzpolice.third.DatePickerDialog;
import com.pvirtech.pzpolice.ui.adapter.MyAssessmentAdapter;
import com.pvirtech.pzpolice.ui.adapter.MyAssessmentListAdapter;
import com.pvirtech.pzpolice.ui.appInterfaces.OnSelectedListener;
import com.pvirtech.pzpolice.ui.base.BaseActivity;
import com.pvirtech.pzpolice.utils.ListUtils;
import com.pvirtech.pzpolice.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * 我的考核
 */
public class MyAssessmentActivity extends BaseActivity {
    MyAssessmentAdapter myAssessmentAdapter;
    MyAssessmentListAdapter adapter;
    List<MyAssessmentEntity> listType = new ArrayList<>();
    String type = "";
    int indexO = 0;
    int mCurrentCounter = 0;
    int TOTAL_COUNT = 0;
    List<MyAssessmentListEntity> list = new ArrayList();
    String userid;
    @BindView(R.id.tv_info_date)
    TextView tvInfoDate;
    @BindView(R.id.lin_chose_date)
    LinearLayout linChoseDate;
    @BindView(R.id.recycleviewType)
    RecyclerView recycleviewType;
    @BindView(R.id.recycleviewTask)
    RecyclerView recycleviewTask;
    ArrayList<MyTasksEntity> mData = new ArrayList<>();
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.imageView3)
    ImageView imageView3;
    @BindView(R.id.family_member)
    TextView familyMember;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    ScoreEntity scoreEntity;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_soore)
    TextView tvSoore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_assessment);
        ButterKnife.bind(this);
        initTitleView("我的考核");
        mContext = MyAssessmentActivity.this;
        TAG = "MyAssessmentActivity";

        recycleviewTask.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        recycleviewTask.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL));
        recycleviewTask.setLayoutManager(new GridLayoutManager(mContext, 1));
        initData();
        initView();
        getMyRecord();
    }

    private void initData() {
        Intent intent = getIntent();
        scoreEntity = intent.getParcelableExtra(Constant.INTENT_BUNDLE);
        if (null == scoreEntity) {
            userid = AppValue.getInstance().getmUserInfo().getUSER_ID();
        } else {
            userid = scoreEntity.getUserId();
            tvName.setText(scoreEntity.getName());
            tvSoore.setText(scoreEntity.getTotalScore() + "分");
        }
        type = "0";
    }


    private void initView() {
        /**
         * 处理任务类型概览
         */
        for (MyAssessmentEnum myEnum : MyAssessmentEnum.values()) {
            MyAssessmentEntity entity = myEnum.getValue();
            listType.add(entity);
        }
        listType.get(0).setChecked(true);
        myAssessmentAdapter = new MyAssessmentAdapter(mContext, listType, new MyAssessmentAdapter.OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                List<MyAssessmentEntity> data = myAssessmentAdapter.getList();
                for (MyAssessmentEntity performanceAppraisalEntity : listType) {
                    performanceAppraisalEntity.setChecked(false);
                }

                listType.get(position).setChecked(true);
                myAssessmentAdapter.notifyDataSetChanged();
                type = listType.get(position).getType();

                indexO = 0;
                getMyRecord();
            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });
        recycleviewType.setAdapter(myAssessmentAdapter);
        recycleviewType.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        recycleviewType.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL));
        recycleviewType.setLayoutManager(new GridLayoutManager(mContext, 2));


        //
        adapter = new MyAssessmentListAdapter(mContext, list, new MyAssessmentListAdapter
                .OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });
        recycleviewTask.setAdapter(adapter);
        recycleviewTask.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration
                .VERTICAL));
        recycleviewTask.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration
                .HORIZONTAL));
        recycleviewTask.setLayoutManager(new GridLayoutManager(mContext, 1));

        tvInfoDate.setText(TimeUtil.getYM());
    }


    @OnClick({R.id.lin_chose_date})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_chose_date:
                DatePickerDialog pickerDialog = new DatePickerDialog();
                pickerDialog.showDialogYM(this, tvInfoDate.getText().toString(), new OnSelectedListener() {
                    @Override
                    public void onSelected(String data) {
                        tvInfoDate.setText(data);
                        indexO = 0;
                        getMyRecord();
                    }
                });
                break;
        }
    }

    private void getMyRecord() {
        adapter.setList(new ArrayList<MyAssessmentListEntity>());
        showLoading("");
        JsonObject data = new JsonObject();
        data.addProperty("userid", userid);
        data.addProperty("date", tvInfoDate.getText().toString());
        if (!TextUtils.isEmpty(type)) {
            data.addProperty("type", type);
        }
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);
        getCompositeDisposable().add(RetrofitHttp.provideClientApi().apprankMyscorelist(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<List<MyAssessmentListEntity>>>() {
            @Override
            public void accept(@NonNull HttpResult<List<MyAssessmentListEntity>> response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    if (!ListUtils.isEmpty(response.getData())) {
                        adapter.setList(response.getData());
                    }
                    hideLoading();
                } else {
                    showError(response.getResultMessage());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                showError("");
            }
        }));
    }

}

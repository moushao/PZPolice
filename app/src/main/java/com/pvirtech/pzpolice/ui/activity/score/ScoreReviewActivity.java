package com.pvirtech.pzpolice.ui.activity.score;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.MyscoreEntity;
import com.pvirtech.pzpolice.main.Constant;
import com.pvirtech.pzpolice.ui.adapter.ScoreReviewAdapter;
import com.pvirtech.pzpolice.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ScoreReviewActivity extends BaseActivity {
    ScoreReviewAdapter adapter;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    List<MyscoreEntity> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_review);
        mContext = ScoreReviewActivity.this;
        initTitleView("积分详细");
        TAG = "ScoreReviewActivity";

        String id = "";
        try {
            Intent intent = getIntent();
            id = intent.getStringExtra(Constant.INTENT_BUNDLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        initeView();
        if (!TextUtils.isEmpty(id)) {
            getData(id);
        }
    }

    private void initeView() {
        adapter = new ScoreReviewAdapter(mContext, list, new ScoreReviewAdapter
                .OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });
        recycleview.setAdapter(adapter);
        recycleview.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration
                .VERTICAL));
        recycleview.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration
                .HORIZONTAL));
        recycleview.setLayoutManager(new GridLayoutManager(mContext, 1));
    }

    private void getData(String id) {
       /* showLoading("");
        JsonObject data = new JsonObject();
        data.addProperty("userid", id);
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);
        getCompositeDisposable().add(RetrofitHttp.provideClientApi().apprankMyscorelist(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<List<MyscoreEntity>>>() {
            @Override
            public void accept(@NonNull HttpResult<List<MyscoreEntity>> response) throws Exception {
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
        }));*/
    }
}

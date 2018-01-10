package com.pvirtech.pzpolice.ui.presenter;

import com.pvirtech.pzpolice.http.HttpSubmit;
import com.pvirtech.pzpolice.http.HttpSubmitUtil;
import com.pvirtech.pzpolice.ui.contract.SelectPersonActivityContract;
import com.pvirtech.pzpolice.ui.model.SelectPersonActivityModelImpl;

/**
 * Created by Administrator on 2017/03/24
 */

public class SelectPersonActivityPresenterImpl implements SelectPersonActivityContract.Presenter {
    SelectPersonActivityModelImpl model = new SelectPersonActivityModelImpl();
    SelectPersonActivityContract.View view;

    public SelectPersonActivityPresenterImpl(SelectPersonActivityContract.View view) {
        this.view = view;
    }


    @Override
    public void initData() {
        view.viewShowLoading("");
        /**
         * 处理提交数据
         */
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);
        /**
         * 网路访问登录
         */

       /* Subscription subscribe01 = RetrofitHttp.provideClientApi().getAppUserList(httpSubmit).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                L.d("sucess");
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<HttpResult<List<UserListEntity>>>() {
            @Override
            public void call(HttpResult response) {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    ArrayList<UserListEntity> list = (ArrayList<UserListEntity>) response.getData();
                    view.initData(list);
                    view.viewHideLoading();
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                L.d("failed");
                view.viewShowError("");
            }
        });
        compositeSubscription.add(subscribe01);*/
    }
}
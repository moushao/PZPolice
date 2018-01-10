package com.pvirtech.pzpolice.ui.presenter;


import com.pvirtech.pzpolice.ui.contract.WorkOneContract;
import com.pvirtech.pzpolice.ui.model.WorkOneModelImpl;
import com.pvirtech.pzpolice.utils.L;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrator on 2017/01/05
 */

public class WorkOnePresenterImpl implements WorkOneContract.Presenter {
    WorkOneContract.View view;
    WorkOneContract.Model model = new WorkOneModelImpl();



    public WorkOnePresenterImpl(WorkOneContract.View view) {
        this.view = view;
    }

    @Override
    public void getData() {
        model.setName("a");
        model.setPasswd("b");
        boolean isTrue = model.checkUserValidity();
        String departid/* = AppValue.getInstance().getUserInfo().getOrganID()*/;
        departid = "JT51GSJT51GStest";
        Map map = new HashMap();
        map.put("depart_id", departid);
        map.put("ly", "");
        map.put("currentPage", String.valueOf(1));
        map.put("showCount", "10");
//        compositeSubscription.add(//
        /*RetrofitHttp.provideClientApi().CaseQueryData(map).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                int a = 0;
                L.d("sucess");
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<HttpResult<List<CaseQueryEntity>>>() {
            @Override
            public void call(HttpResult<List<CaseQueryEntity>> response) {
                String s = response.getResultCode();
                List<CaseQueryEntity> a = response.getData();
                L.d("sucess");
                Gson gson = new Gson();

                view.getDataSucessed(gson.toJson(a));
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                view.getDataFailed("");
            }
        });*/
//        view.onLoginResult(true, 1);
        L.d("doLogin in presenter");
    }
}
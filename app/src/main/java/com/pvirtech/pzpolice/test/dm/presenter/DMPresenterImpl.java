package com.pvirtech.pzpolice.test.dm.presenter;


import com.pvirtech.pzpolice.test.dm.contract.DMContract;
import com.pvirtech.pzpolice.test.dm.model.DMModelImpl;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class DMPresenterImpl implements DMContract.Presenter {
    private DMContract.View loginView;
    private DMModelImpl user;

    @Inject
    public DMPresenterImpl(DMContract.View view) {
        loginView = view;
        user = new DMModelImpl("张三", "123456");
    }

    @Override
    public void clear() {
        loginView.onClearText();
    }

    @Override
    public void doLogin(String name, String password) {
        boolean result = false;
        int code = 0;
       /* if (name.equals(user.getName()) && password.equals(user.getPassword())) {
            result = true;
            code = 1;
        } else {
            result = false;
            code = 0;
        }

        loginView.onLoginResult(result, code);*/
        String departid/* = AppValue.getInstance().getUserInfo().getOrganID()*/;
        departid = "JT51GSJT51GStest";
        Map map = new HashMap();
        map.put("depart_id", departid);
        map.put("ly", "");
        map.put("currentPage", String.valueOf(1));
        map.put("showCount", "10");
//        compositeSubscription.add(//
        /*RetrofitHttp.provideClientApi().CaseQueryData(map)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        int a = 0;
                        L.d("sucess");
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HttpResult<List<CaseQueryEntity>>>() {
                    @Override
                    public void call(HttpResult<List<CaseQueryEntity>> response) {
                        String s = response.getResultCode();
                        response.getData();
                        L.d("sucess");
                        loginView.onLoginResult(true, 1);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        L.d("sucess");
                    }
                });*/
    }
}
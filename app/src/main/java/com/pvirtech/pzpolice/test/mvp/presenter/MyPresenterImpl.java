package com.pvirtech.pzpolice.test.mvp.presenter;


import com.pvirtech.pzpolice.test.mvp.contract.MyContract;
import com.pvirtech.pzpolice.test.mvp.model.MyModelImpl;
import com.pvirtech.pzpolice.utils.L;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by 尤鹏达 on 2016/11/22.
 * 邮箱：3340418505@qq.com
 */

public class MyPresenterImpl implements MyContract.Presenter {
    MyContract.View view;
    MyModelImpl myModelImpl;

    @Inject
    public MyPresenterImpl(MyContract.View view) {
        this.view = view;
        init();
    }


    @Override
    public void doLogin(String name, String passwd) {
        myModelImpl.setName(name);
        myModelImpl.setPasswd(passwd);
        myModelImpl.checkUserValidity(name, passwd);


//        llloading.setVisibility(View.VISIBLE);
        String departid/* = AppValue.getInstance().getUserInfo().getOrganID()*/;
        departid = "JT51GSJT51GStest";
        Map map = new HashMap();
        map.put("depart_id", departid);
        map.put("ly", "");
        map.put("currentPage", String.valueOf(1));
        map.put("showCount", "10");
//        compositeSubscription.add(//
      /*  RetrofitHttp.provideClientApi().CaseQueryData(map)
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
                                view.onLoginResult(true, 1);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        L.d("sucess");
                    }
                });
//        view.onLoginResult(true, 1);
        L.d("doLogin in presenter");*/
    }

    @Override
    public void clear() {
        L.d("clear in presenter");
    }

    private boolean init() {
        myModelImpl = new MyModelImpl("", "");
        return true;
    }
}
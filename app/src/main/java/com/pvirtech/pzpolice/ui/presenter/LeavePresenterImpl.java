package com.pvirtech.pzpolice.ui.presenter;

import com.pvirtech.pzpolice.main.Constant;
import com.pvirtech.pzpolice.main.OperationUtils;
import com.pvirtech.pzpolice.ui.contract.LeaveContract;
import com.pvirtech.pzpolice.ui.model.LeaveModelImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/02/28
 */

public class LeavePresenterImpl implements LeaveContract.Presenter {
    LeaveModelImpl model = new LeaveModelImpl();
    LeaveContract.View view;

    public LeavePresenterImpl(LeaveContract.View view) {
        this.view = view;
    }




    @Override
    public void submit(String strLeaveType, String strStartTime, String strEndTime, String strLeaveReason) {
        model = new LeaveModelImpl(strLeaveType, strStartTime, strEndTime, strLeaveReason, null);

        if (!Constant.SUCCESS.equals(model.checkValidity())) {
            view.showWarning(model.checkValidity());
            return;
        }

        String checkResult = OperationUtils.compareTime(strStartTime, strEndTime);
        if (!Constant.SUCCESS.equals(checkResult)) {
            view.showWarning(checkResult);
            return;
        }


        Map map = new HashMap();
       /* Subscription subscribe01 = RetrofitHttp.provideClientApi().CaseQueryData(map).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                int a = 0;
                L.d("sucess");
                view.submitting();
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<HttpResult<List<CaseQueryEntity>>>() {
            @Override
            public void call(HttpResult<List<CaseQueryEntity>> response) {
                String s = response.getResultCode();
                response.getData();
                L.d("sucess");
                view.submitSuccess();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                L.d("sucess");
                view.submitFailed();
            }
        });
        compositeSubscription.add(subscribe01);*/
    }
}
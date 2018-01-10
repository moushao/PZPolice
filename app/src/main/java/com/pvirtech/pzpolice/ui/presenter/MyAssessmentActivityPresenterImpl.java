package com.pvirtech.pzpolice.ui.presenter;

import com.pvirtech.pzpolice.ui.contract.MyAssessmentActivityContract;
import com.pvirtech.pzpolice.ui.model.MyAssessmentActivityModelImpl;

/**
 * Created by Administrator on 2017/03/22
 */

public class MyAssessmentActivityPresenterImpl implements MyAssessmentActivityContract.Presenter {
    MyAssessmentActivityModelImpl model = new MyAssessmentActivityModelImpl();
    MyAssessmentActivityContract.View view;

    public MyAssessmentActivityPresenterImpl(MyAssessmentActivityContract.View view) {
        this.view = view;
    }
}
package com.pvirtech.pzpolice.ui.presenter;

import com.pvirtech.pzpolice.ui.contract.PerformanceAppraisalActivityContract;
import com.pvirtech.pzpolice.ui.model.PerformanceAppraisalActivityModelImpl;

/**
 * Created by Administrator on 2017/03/22
 */

public class PerformanceAppraisalActivityPresenterImpl implements PerformanceAppraisalActivityContract.Presenter {
    PerformanceAppraisalActivityModelImpl model = new PerformanceAppraisalActivityModelImpl();
    PerformanceAppraisalActivityContract.View view;

    public PerformanceAppraisalActivityPresenterImpl(PerformanceAppraisalActivityContract.View view) {
        this.view = view;
    }
}
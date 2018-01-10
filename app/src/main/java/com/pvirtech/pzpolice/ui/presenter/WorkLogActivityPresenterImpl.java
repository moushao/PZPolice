package com.pvirtech.pzpolice.ui.presenter;

import com.pvirtech.pzpolice.ui.contract.WorkLogActivityContract;
import com.pvirtech.pzpolice.ui.model.WorkLogActivityModelImpl;

/**
 * Created by Administrator on 2017/03/22
 */

public class WorkLogActivityPresenterImpl implements WorkLogActivityContract.Presenter {
    WorkLogActivityModelImpl model = new WorkLogActivityModelImpl();
    WorkLogActivityContract.View view;

    public WorkLogActivityPresenterImpl(WorkLogActivityContract.View view) {
        this.view = view;
    }
}
package com.pvirtech.pzpolice.ui.presenter;

import com.pvirtech.pzpolice.ui.contract.DutyFromActivityContract;
import com.pvirtech.pzpolice.ui.model.DutyFromActivityModelImpl;

/**
 * Created by Administrator on 2017/03/22
 */

public class DutyFromActivityPresenterImpl implements DutyFromActivityContract.Presenter {
    DutyFromActivityModelImpl model = new DutyFromActivityModelImpl();
    DutyFromActivityContract.View view;

    public DutyFromActivityPresenterImpl(DutyFromActivityContract.View view) {
        this.view = view;
    }
}
package com.pvirtech.pzpolice.ui.presenter;

import com.pvirtech.pzpolice.ui.contract.DealTaskActivityContract;
import com.pvirtech.pzpolice.ui.model.DealTaskActivityModelImpl;

/**
 * Created by Administrator on 2017/03/23
 */

public class DealTaskActivityPresenterImpl implements DealTaskActivityContract.Presenter {
    DealTaskActivityModelImpl model = new DealTaskActivityModelImpl();
    DealTaskActivityContract.View view;

    public DealTaskActivityPresenterImpl(DealTaskActivityContract.View view) {
        this.view = view;
    }
}
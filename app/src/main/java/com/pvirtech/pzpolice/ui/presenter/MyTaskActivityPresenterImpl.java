package com.pvirtech.pzpolice.ui.presenter;

import com.pvirtech.pzpolice.ui.contract.MyTaskActivityContract;
import com.pvirtech.pzpolice.ui.model.MyTaskActivityModelImpl;

/**
 * Created by Administrator on 2017/03/22
 */

public class MyTaskActivityPresenterImpl implements MyTaskActivityContract.Presenter {
    MyTaskActivityModelImpl model = new MyTaskActivityModelImpl();
    MyTaskActivityContract.View view;

    public MyTaskActivityPresenterImpl(MyTaskActivityContract.View view) {
        this.view = view;
    }
}
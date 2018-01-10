package com.pvirtech.pzpolice.ui.presenter;

import com.pvirtech.pzpolice.ui.contract.MyEquipmentActivityContract;
import com.pvirtech.pzpolice.ui.model.MyEquipmentActivityModelImpl;

/**
 * Created by Administrator on 2017/03/22
 */

public class MyEquipmentActivityPresenterImpl implements MyEquipmentActivityContract.Presenter {
    MyEquipmentActivityModelImpl model = new MyEquipmentActivityModelImpl();
    MyEquipmentActivityContract.View view;

    public MyEquipmentActivityPresenterImpl(MyEquipmentActivityContract.View view) {
        this.view = view;
    }
}
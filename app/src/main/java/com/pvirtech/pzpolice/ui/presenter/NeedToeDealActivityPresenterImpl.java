package com.pvirtech.pzpolice.ui.presenter;

import com.pvirtech.pzpolice.ui.contract.NeedToeDealActivityContract;
import com.pvirtech.pzpolice.ui.model.NeedToeDealActivityModelImpl;

/**
 * Created by Administrator on 2017/03/22
 */

public class NeedToeDealActivityPresenterImpl implements NeedToeDealActivityContract.Presenter {
    NeedToeDealActivityModelImpl model = new NeedToeDealActivityModelImpl();
    NeedToeDealActivityContract.View view;

    public NeedToeDealActivityPresenterImpl(NeedToeDealActivityContract.View view) {
        this.view = view;
    }
}
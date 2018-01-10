package com.pvirtech.pzpolice.ui.contract;

import com.pvirtech.pzpolice.entity.UserListEntity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/24.
 */

public class SelectPersonActivityContract {
    public interface View extends BaseContractView {
        void initData(ArrayList<UserListEntity> list);

    }

    public interface Presenter {
        void initData();
    }

    public interface Model {

    }


}
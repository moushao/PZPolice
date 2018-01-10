package com.pvirtech.pzpolice.ui.contract;

import com.pvirtech.pzpolice.entity.WorkDay;

import java.util.List;

/**
 * Created by Administrator on 2017/3/22.
 */

public class WorkTimeActivityContract {
    public interface View extends BaseContractView {

    }

    public interface Presenter {
        List<WorkDay> initDays(String date);
    }

    public interface Model {

    }


}